package it.neef.tu.ba;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.hadoop.mapred.HadoopInputFormat;
import org.apache.flink.api.java.hadoop.mapred.HadoopOutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileStatus;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import java.io.File;

/**
* Reads all files from a directory (args[0]) and merges them into one outputfile (args[1])
 */
public class Job {

    private static final String BZIP2 = "bz2";
    private static final String GZIP = "gz";
    private static final String PLAIN = "plain";

	public static void main(String[] args) throws Exception {
		// set up the execution environment
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();


		if(args.length != 3) {
			System.err.println("USAGE: Job <input-dir/> <outputfile> <"+PLAIN+"/"+BZIP2+"/"+GZIP+">");
			return;
		}

		Path inputDir = new Path(args[0]);

        FileSystem dirFS = inputDir.getFileSystem();

        if (! dirFS.exists(inputDir)) {
            System.err.println("Input directory is not a directory!");
            return;
        }

        File outputFile = new File(args[1]);

        if ( outputFile.exists() ) {
            System.err.println("Outputfile exists. Not overwriting it!");
            return ;
        }


        if ( ! (args[2].equalsIgnoreCase(PLAIN) || args[2].equalsIgnoreCase(BZIP2) || args[2].equalsIgnoreCase(GZIP))) {
            System.err.println("Compression codec must be either: plain or bzip2 (bz2) or gzip (gz)");
            return ;
        }


        //Iterate over all files in the directory
        FileStatus[] inputFiles = dirFS.listStatus(inputDir);

        if( inputFiles == null) {
            System.err.println("No files found");
            return;
        }

        HadoopInputFormat<LongWritable, Text> inputFormat = new HadoopInputFormat<>(new TextInputFormat(), LongWritable.class, Text.class, new JobConf());


        for(FileStatus fileStatus : inputFiles) {

            if (fileStatus.isDir()) {
                //Ignore directories subdirectories.
                continue;
            }

            TextInputFormat.addInputPath(inputFormat.getJobConf(), new org.apache.hadoop.fs.Path(fileStatus.getPath().toString()));

        }

        DataSet<Tuple2<NullWritable, Text>> outputData = env.createInput(inputFormat).map(new MapFunction<Tuple2<LongWritable, Text>, Tuple2<NullWritable, Text>>() {
            @Override
            public Tuple2<NullWritable, Text> map(Tuple2<LongWritable, Text> longWritableTextTuple2) throws Exception {
                return new Tuple2<>(NullWritable.get(), longWritableTextTuple2.f1);
            }
        });

        HadoopOutputFormat<NullWritable, Text> outputFormat = new HadoopOutputFormat<>( new org.apache.hadoop.mapred.TextOutputFormat<NullWritable, Text>(), new JobConf());
        JobConf config = outputFormat.getJobConf();

        //Don't compress if it's plain.
        if ( ! args[2].equalsIgnoreCase(PLAIN)) {
            TextOutputFormat.setCompressOutput(config, true);

            if (args[2].equalsIgnoreCase(BZIP2)) {
                //Compress with bzip2
                TextOutputFormat.setOutputCompressorClass(config, BZip2Codec.class);
            } else {
                //Compress with gzip
                TextOutputFormat.setOutputCompressorClass(config, GzipCodec.class);
            }
        }


        TextOutputFormat.setOutputPath(config, new org.apache.hadoop.fs.Path(args[1]));


        //Write the data into an output file
        outputData.output(outputFormat);
		// execute program
		env.execute("FileMerger execution");
	}
}

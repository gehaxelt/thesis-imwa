package it.neef.tub.dima.ba.imwa.impl.parser;

import org.apache.commons.io.Charsets;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Seekable;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.mapred.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

//import org.apache.mahout.text.wikipedia.XmlInputFormat;

/**
 * Reads records that are delimited by a specific begin/end tag.
 * Taken from and adapted: https://github.com/apache/mahout/blob/master/integration/src/main/java/org/apache/mahout/text/wikipedia/XmlInputFormat.java
 * <p>
 * Last access: 09.02.2017
 */
public class XmlInputFormat extends TextInputFormat {


    /**
     * Configuration start key for xml start tag.
     */
    public static final String START_TAG_KEY = "xmlinput.start";
    /**
     * Configuration stop key for xml stop tag.
     */
    public static final String END_TAG_KEY = "xmlinput.end";
    /**
     * Logger used for logging.
     */
    private static final Logger log = LoggerFactory.getLogger(XmlInputFormat.class);

    @Override
    public org.apache.hadoop.mapred.RecordReader<LongWritable, Text> getRecordReader(org.apache.hadoop.mapred.InputSplit genericSplit, JobConf job, Reporter reporter) throws IOException {
        try {
            // Pass the split to the XmlRecordReader and let it do the XML <page> splitting
            return new XmlRecordReader((FileSplit) genericSplit, job);
        } catch (IOException ioe) {
            log.warn("Error while creating XmlRecordReader", ioe);
            return null;
        }
    }


    /**
     * XMLRecordReader class to read through a given xml document to output xml blocks as records as specified
     * by the start tag and end tag.
     * This is a mix of https://github.com/apache/mahout/blob/master/integration/src/main/java/org/apache/mahout/text/wikipedia/XmlInputFormat.java (XML tag matching)
     * and https://github.com/apache/hadoop/blob/trunk/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-core/src/main/java/org/apache/hadoop/mapreduce/lib/input/LineRecordReader.java (Decompression)
     */
    public static class XmlRecordReader implements RecordReader<LongWritable, Text> {

        /**
         * The start tag to look for.
         */
        private final byte[] startTag;

        /**
         * The end tag to match.
         */
        private final byte[] endTag;
        /**
         * Seekable file position
         */
        private final Seekable filePosition;
        /**
         * Buffer to store a page's XML data
         */
        private final DataOutputBuffer buffer = new DataOutputBuffer();
        /**
         * Factory for (de)compression codecs
         */
        private CompressionCodecFactory compressionCodecs;
        /**
         * Split's start position from which to read.
         */
        private long start;
        /**
         * Current read position.
         */
        private long pos;
        /**
         * Split's end position.
         */
        private long end;
        /**
         * InputStream from which to read.
         */
        private InputStream in;
        /**
         * FileSystem stream for reading
         */
        private FSDataInputStream fileIn;
        /**
         * Compression codec used for decompressing
         */
        private CompressionCodec codec;
        /**
         * Decompressor used for decompression of compressed data
         */
        private Decompressor decompressor;

        /**
         * XmlRecordReader constructor for splitting the data set.
         *
         * @param split the FileSplit to process
         * @param conf  the Job configuration to use
         * @throws IOException if an error occurred while processing the data
         */
        public XmlRecordReader(FileSplit split, JobConf conf) throws IOException {
            // Get the start and end tag from the configuration.
            this.startTag = conf.get(START_TAG_KEY).getBytes(Charsets.UTF_8);
            this.endTag = conf.get(END_TAG_KEY).getBytes(Charsets.UTF_8);

            // Get the split's start and end position
            this.start = split.getStart();
            this.end = start + split.getLength();

            // Open the file and look it for compressed data.
            Path file = split.getPath();
            this.compressionCodecs = new CompressionCodecFactory(conf);
            this.codec = this.compressionCodecs.getCodec(file);
            FileSystem fs = file.getFileSystem(conf);
            this.fileIn = fs.open(file);

            // Check if the file is compressed.
            if (this.isCompressedInput()) {
                this.decompressor = CodecPool.getDecompressor(this.codec);
                if (this.codec instanceof SplittableCompressionCodec) {
                    // Splittable compression is favorable, but we have to adjust the start and end positions.
                    SplitCompressionInputStream cIn = ((SplittableCompressionCodec) this.codec).createInputStream(this.fileIn, this.decompressor, this.start, this.end, SplittableCompressionCodec.READ_MODE.BYBLOCK);
                    this.in = cIn;
                    this.start = cIn.getAdjustedStart();
                    this.end = cIn.getAdjustedEnd();
                    this.filePosition = cIn;
                } else {
                    // Data is not splittable, but create an InputStream anyway, so we read the actual data.
                    this.in = this.codec.createInputStream(this.fileIn, this.decompressor);
                    this.filePosition = this.fileIn;
                }
            } else {
                //If not compressed, just adjust the reading position.
                this.fileIn.seek(this.start);
                this.in = this.fileIn;
                this.filePosition = this.fileIn;
            }

        /*if(this.start != 0L) {
            this.start += (long)this.in.readLine(new Text(), 0, this.maxBytesToConsume(this.start));
        }*/

            // The current position should be the start.
            this.pos = this.start;
        }


        /**
         * Reads data from the InputStream until a match is found.
         *
         * @param match       the marker until data is read.
         * @param withinBlock If true, the data will be saved to the DataOutputStream buffer.
         * @return True if match was found, false otherwise.
         * @throws IOException if there is an error reading the data.
         */
        private boolean readUntilMatch(byte[] match, boolean withinBlock) throws IOException {
            int i = 0;
            while (true) {
                int b = this.in.read();
                // end of file:
                if (b == -1) {
                    return false;
                }
                // save to buffer:
                if (withinBlock) {
                    this.buffer.write(b);
                }

                // check if we're matching:
                if (b == match[i]) {
                    i++;
                    if (i >= match.length) {
                        return true;
                    }
                } else {
                    i = 0;
                }
                // see if we've passed the stop point:
                if (!withinBlock && i == 0 && this.getFilePosition() >= this.end) {
                    return false;
                }
            }
        }

        /**
         * Reads the next element from the input file.
         *
         * @param key   the wrapper for the key value.
         * @param value the wrapper for the text value.
         * @return True if there are still new elements to be created. False if none.
         * @throws IOException If an error occurs while processing the data.
         */
        public synchronized boolean next(LongWritable key, Text value) throws IOException {
            while (this.getFilePosition() <= this.end && this.readUntilMatch(startTag, false)) {
                // We are not at the end and we can find a new opening tag.
                try {
                    // Write the start tag.
                    this.buffer.write(startTag);
                    // Now try to read until the end. If that succeeds we can return the new element.
                    if (this.readUntilMatch(endTag, true)) {
                        key.set(this.pos);
                        value.set(this.buffer.getData(), 0, this.buffer.getLength());
                        this.pos += this.buffer.getLength();
                        return true;
                    }
                } finally {
                    // Always clear the output buffer for the next element.
                    this.buffer.reset();
                }
            }
            // There are no more elements left.
            return false;
        }

        /**
         * Checks if the input is compressed.
         *
         * @return True if so, false otherwise.
         */
        private boolean isCompressedInput() {
            return this.codec != null;
        }

        @Override
        public LongWritable createKey() {
            return new LongWritable();
        }

        @Override
        public Text createValue() {
            return new Text();
        }

        /**
         * Getter for obtaining the current read position in the file.
         *
         * @return the current file position.
         * @throws IOException If an error occurs.
         */
        private long getFilePosition() throws IOException {
            long retVal;
            if (this.isCompressedInput() && null != this.filePosition) {
                retVal = this.filePosition.getPos();
            } else {
                retVal = getPos();
            }

            return retVal;
        }

        /**
         * Getter for the input reader's progress.
         *
         * @return the progress within [0, 1]
         * @throws IOException If an error occurs.
         */
        public synchronized float getProgress() throws IOException {
            return this.start == this.end ? 0.0F : Math.min(1.0F, (float) (this.getFilePosition() - this.start) / (float) (this.end - this.start));
        }

        /**
         * Getter for the current position.
         *
         * @return this.pos' value
         * @throws IOException If an error occurs.
         */
        public synchronized long getPos() throws IOException {
            return this.pos;
        }

        /**
         * Closes the input stream and releases the decompressor.
         *
         * @throws IOException If an error occurs.
         */
        public synchronized void close() throws IOException {
            try {
                if (this.in != null) {
                    this.in.close();
                }
            } finally {
                if (this.decompressor != null) {
                    CodecPool.returnDecompressor(this.decompressor);
                }

            }

        }
    }
}

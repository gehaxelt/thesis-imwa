package it.neef.tub.dima.ba.imwa.impl.parser;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
/**
 * Wrapper class for the XmlInputFormat to set the correct
 * start and end tags. For wikipedia pages, this is <page> </page>
 * <p>
 * Created by gehaxelt on 09.10.16.
 **/
public class PageXMLInputFormat extends XmlInputFormat {


    @Override
    public org.apache.hadoop.mapred.RecordReader<LongWritable, Text> getRecordReader(org.apache.hadoop.mapred.InputSplit genericSplit, JobConf job, Reporter reporter) throws IOException {
        job.set(START_TAG_KEY, "<page>");
        job.set(END_TAG_KEY, "</page>");
        return super.getRecordReader(genericSplit, job, reporter);
    }
}

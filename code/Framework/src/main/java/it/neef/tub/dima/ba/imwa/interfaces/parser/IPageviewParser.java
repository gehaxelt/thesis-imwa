package it.neef.tub.dima.ba.imwa.interfaces.parser;

import it.neef.tub.dima.ba.imwa.impl.configuration.Configuration;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;

/**
 * Interface for a wikipedia pageview parser
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IPageviewParser<V extends IPageview> extends Serializable {

    /**
     * Function which should parse the wikipedia pageview data
     * referenced by the configured pageviewDumpPath and pageviewDumpShortTag.
     *
     * @throws Exception If an error occurs while parsing the data.
     * @see Configuration
     */
    void parsePageviewData() throws Exception;

    /**
     * Getter for the parsed pageview data.
     *
     * @return A DataSet of parsed pageview objects
     */
    DataSet<V> getPageviews();


}

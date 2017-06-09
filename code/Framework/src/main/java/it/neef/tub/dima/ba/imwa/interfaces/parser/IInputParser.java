package it.neef.tub.dima.ba.imwa.interfaces.parser;


import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.impl.configuration.Configuration;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;

/**
 * Interface for an InputParser class which orchestrates the parsing of the wikipedia
 * dump and pageview data. It should also handle the process of merging the datasets
 * and populating the DataHolder with DataSets of Pages, Revisions and Contributors.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IInputParser<P extends IPage> extends Serializable {

    /**
     * Runs the DumpParser on the wikipedia dump file referenced by the configuration's
     * wikipediaDumpPath.
     *
     * @throws Exception If an error occurs while parsing the data.
     * @see Configuration
     */
    void runDumpParser() throws Exception;

    /**
     * Runs the PageviewParser on the wikipedia pageview file referenced by the configuration's
     * pageviewDumpPath and pageviewDumpShortTag.
     *
     * @throws Exception If an error occurs while parsing the data.
     * @see Configuration
     */
    void runPageviewParser() throws Exception;

    /**
     * Handles the process of merging the parsed dump DataSet with the parsed pageview DataSet.
     *
     * @return The page DataSet where each page item has a pageview item assigned if a match was found.
     * Otherwise the page's pageview item should be null.
     * @throws Exception If an error occurs while merging the DataSets.
     */
    DataSet<P> mergeDumpAndPageviewData() throws Exception;

    /**
     * This method should populate the DataHolder with the resulting data of mergeDumpAndPageviewData process.
     * It should create three separate DataSets: Pages, Revisions, Contributors
     *
     * @throws Exception If an error occurs while populating the DataHolder.
     */
    void populateDataHolder() throws Exception;
}

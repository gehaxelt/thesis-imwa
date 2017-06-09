package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.*;
import it.neef.tub.dima.ba.imwa.interfaces.parser.AInputParser;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IDumpParser;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IPageviewParser;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.util.Collector;

/**
 * This class extends the AInputParser and coordinates the parsing of the
 * wikipedia dump and the pageviews dump. It also populates the DataHolder.
 *
 * @see AInputParser
 * Created by gehaxelt on 09.10.16.
 */
public class InputParser extends AInputParser {

    /**
     * The IDumpParser object that will be used to parse the wikipedia dump.
     */
    private IDumpParser<IPage> dumpParser;

    /**
     * The IPageviewParser object that will be used to parse the pageviews dump.
     */
    private IPageviewParser<IPageview> pageviewParser;

    /**
     * The IDataHolder object which will be populated after parsing the data has finished.
     */
    private IDataHolder<IPage, IRevision, IContributor> dataHolder;

    /**
     * The constructor assigns the dumpParser, pageviewParser, dataHolder from the framework's configuration.
     */
    public InputParser() {
        this.dumpParser = Framework.getInstance().getConfiguration().getDumpParserFactory().newDumpParser();
        this.pageviewParser = Framework.getInstance().getConfiguration().getPageviewParserFactory().newPageviewParser();
        this.dataHolder = Framework.getInstance().getConfiguration().getDataHolder();
    }

    @Override
    public void runDumpParser() throws Exception {
        this.dumpParser.parseDumpData();
    }

    @Override
    public void runPageviewParser() throws Exception {
        if(Framework.getInstance().getConfiguration().getPageviewDumpPath() != null) {
            this.pageviewParser.parsePageviewData();
        }
    }

    /**
     * Handles the process of merging the parsed dump DataSet with the parsed pageview DataSet.
     * It uses the page tiles as keys.
     *
     * @return The page DataSet where each page item has a pageview item assigned if a match was found.
     * Otherwise the page's pageview item is null.
     * @throws Exception If an error occurs while merging the DataSets.
     */
    @Override
    public DataSet<IPage> mergeDumpAndPageviewData() throws Exception {
        if(Framework.getInstance().getConfiguration().getPageviewDumpPath() != null) {
            return this.dumpParser.getPages().leftOuterJoin(this.pageviewParser.getPageviews())
                    .where(new PageKeySelector())
                    .equalTo(new PageViewKeySelector())
                    .with(new PageAndPageviewJoinFunction());
        }
        return this.dumpParser.getPages();
    }

    @Override
    public void populateDataHolder() throws Exception {
        this.dataHolder.setPages(this.getFilteredIpageDataSet());
        this.dataHolder.setRevisions(this.getRevisions());
        this.dataHolder.setContributors(this.getContributors());
    }

    /**
     * Extracts all IRevisions from all parsed page objects.
     *
     * @return a IRevision DataSet containing all revisions of all pages.
     */
    private DataSet<IRevision> getRevisions() {
        return this.getFilteredIpageDataSet().flatMap(new FlatMapFunction<IPage, IRevision>() {
            @Override
            public void flatMap(IPage iPage, Collector<IRevision> collector) throws Exception {
                for (Object r : iPage.getRevisions()) {
                    collector.collect((IRevision) r);
                }
            }
        });
    }

    /**
     * Extracts all IContributors from all parsed page objects.
     *
     * @return a IContributor DataSet containing all contributors.
     */
    private DataSet<IContributor> getContributors() {
        return this.getFilteredIpageDataSet().flatMap(new FlatMapFunction<IPage, IContributor>() {
            @Override
            public void flatMap(IPage iPage, Collector<IContributor> collector) throws Exception {
                for (Object revision : iPage.getRevisions()) {
                    collector.collect(((IRevision) revision).getContributor());
                }
            }
        });
    }

    /**
     * This class selects a page's title as the key.
     */
    private static class PageKeySelector implements KeySelector<IPage, String> {
        @Override
        public String getKey(IPage iPage) throws Exception {
            return iPage.getTitle();
        }
    }

    /**
     * This class selects a pageview's title as the key.
     */
    private static class PageViewKeySelector implements KeySelector<IPageview, String> {
        @Override
        public String getKey(IPageview iPageview) throws Exception {
            return iPageview.getPageTitle();
        }
    }

    /**
     * This class joins the page and pageview DataSets by assiging a pageview object to its
     * matching page object.
     */
    private static class PageAndPageviewJoinFunction implements JoinFunction<IPage, IPageview, IPage> {
        @Override
        public IPage join(IPage iPage, IPageview iPageview) throws Exception {
            iPage.setPageview(iPageview);
            return iPage;
        }
    }

}

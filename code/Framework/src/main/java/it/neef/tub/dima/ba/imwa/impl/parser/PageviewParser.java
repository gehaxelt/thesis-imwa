package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IPageviewParser;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;

/**
 * Class for parsing pageview information from the pageview datasets.
 *
 * @see IPageviewParser
 * Created by gehaxelt on 09.10.16.
 */
public class PageviewParser implements IPageviewParser<IPageview> {

    /**
     * This will contain the parsed pageview DataSet.
     */
    private DataSet<IPageview> pageviewDataSet = null;

    @Override
    public void parsePageviewData() throws Exception {
        // Get the pageview data path from the configuration.
        DataSet<String> pageViews =
                Framework.getInstance().getConfiguration().getExecutionEnvironment().readTextFile(Framework.getInstance().getConfiguration().getPageviewDumpPath());

        // Get the wiki's shorttag from the configuration.
        // This will be used to filter out the relevant pageview data.
        final String wikiShortTag = Framework.getInstance().getConfiguration().getPageviewDumpShortTag();

        if (wikiShortTag != null) {
            this.parseWithFilter(pageViews, wikiShortTag);
        } else {
            this.parseWithoutFilter(pageViews);
        }
    }

    private void parseWithFilter(DataSet<String> pageViews, String shortTag) {
        pageviewDataSet = pageViews.map(new PageviewMapFunction())
                .filter(new PageviewFilterFunction(shortTag))
                .groupBy(new PageviewKeySelector())
                .reduce(new PageviewReduceFunction());
    }

    private void parseWithoutFilter(DataSet<String> pageViews) {
        pageviewDataSet = pageViews.map(new PageviewMapFunction())
                .filter(new FilterFunction<IPageview>() {
                    @Override
                    public boolean filter(IPageview iPageview) throws Exception {
                        return iPageview != null;
                    }
                })
                .groupBy(new PageviewKeySelector())
                .reduce(new PageviewReduceFunction());
    }

    private class PageviewMapFunction implements MapFunction<String, IPageview> {
        @Override
        public IPageview map(String s) throws Exception {
            try {
                // The format is: [wiki shorttag] [title] [count] [size], so split at a whitespace.
                String[] parts = s.split(" ");
                // Get new pageview instance
                IPageview pv = Framework.getInstance().getConfiguration().getPageviewFactory().newPageview();
                pv.setProjectName(parts[0]);
                // The page title is not normalized and urlencoded. We have to revert that, so that it
                // matches the wikipedia (pages) DataSet.
                String pageTitle = parts[1].replace("_", " ");
                pageTitle = java.net.URLDecoder.decode(pageTitle, "UTF-8");
                pv.setPageTitle(pageTitle);
                pv.setRequestCount(Integer.valueOf(parts[2]));
                pv.setRequestSize(Integer.valueOf(parts[3]));
                return pv;
            } catch (Exception e) {
                return null;
            }
        }
    }

    class PageviewFilterFunction implements FilterFunction<IPageview> {

        private String wikiShortTag;

        PageviewFilterFunction(String shortTag) {
            this.wikiShortTag = shortTag.trim();
        }

        @Override
        public boolean filter(IPageview iPageview) throws Exception {
            // We're only interested in pageviews that match our wiki shorttag.
            return iPageview != null && iPageview.getProjectName().equals(wikiShortTag);
        }
    }

    class PageviewKeySelector implements KeySelector<IPageview, String> {
        @Override
        public String getKey(IPageview iPageview) throws Exception {
            // We want unique entries only, so group by the page title.
            return iPageview.getPageTitle();
        }
    }

    class PageviewReduceFunction implements ReduceFunction<IPageview> {
        @Override
        public IPageview reduce(IPageview iPageview, IPageview t1) throws Exception {
            // Reduce all entries, so we end up with one pageview object per page.
            iPageview.setRequestCount(iPageview.getRequestCount() + t1.getRequestCount());
            iPageview.setRequestSize(iPageview.getRequestSize() + t1.getRequestSize());
            return iPageview;
        }
    }

    @Override
    public DataSet<IPageview> getPageviews() {
        return this.pageviewDataSet;
    }

}

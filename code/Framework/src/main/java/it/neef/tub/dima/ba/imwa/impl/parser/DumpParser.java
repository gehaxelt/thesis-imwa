package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IDumpParser;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * This class parses all pages, revision and contributor data from a wikipedia dump.
 * It also applies the PreFilters before returning the page DataSet.
 *
 * @see IDumpParser
 * Created by gehaxelt on 09.10.16.
 */
public class DumpParser implements IDumpParser<IPage> {

    /**
     * The final parsed page DataSet.
     */
    private DataSet<IPage> pageDataSet = null;

    @Override
    public void parseDumpData() throws Exception {
        // Get wikipedia dump path from configuration and
        // split the xml data at the <page> tags using the PageXMLInputFormat
        DataSet<Tuple2<LongWritable, Text>> pageSplit =
                Framework.getInstance().getConfiguration().getExecutionEnvironment().readHadoopFile(new PageXMLInputFormat(), LongWritable.class, Text.class, Framework.getInstance().getConfiguration().getWikipediaDumpPath());

        pageSplit = pageSplit.distinct(1);

        // Apply PreFilters to the xml-page DataSet.
        // Pages not matching a filter are removed.
        List<IPreFilter> preFilters = Framework.getInstance().getConfiguration().getPreFilters();
        for (final IPreFilter filter : preFilters) {
            pageSplit = pageSplit.filter(new FilterFunction<Tuple2<LongWritable, Text>>() {
                @Override
                public boolean filter(Tuple2<LongWritable, Text> longWritableTextTuple2) throws Exception {
                    return filter.filter(longWritableTextTuple2.f1.toString());
                }
            });
        }

        // Parse the xml page blocks and generate IPage/IRevision/IContributor objects.
        pageDataSet = pageSplit.map(new MapFunction<Tuple2<LongWritable, Text>, IPage>() {
            // We have to pass a separate configuration object with the factories, otherwise they might not be initiated correctly.
            private final DumpParserConfig dumpParserConfig = new DumpParserConfig(Framework.getInstance().getConfiguration().getPageFactory(), Framework.getInstance().getConfiguration().getRevisionFactory(), Framework.getInstance().getConfiguration().getContributorFactory());

            @Override
            public IPage map(Tuple2<LongWritable, Text> longWritableTextTuple2) throws Exception {
                // Parse the <page>...</page> block. It should only return one page object which is not null.
                ArrayList<IPage<IRevision, IPageview>> pages = XMLContentHandler.parseXMLString(longWritableTextTuple2.f1.toString(), this.dumpParserConfig);
                if (pages == null || pages.size() != 1 || pages.get(0) == null) {
                    return null;
                }
                return pages.get(0);
            }
        });
    }


    @Override
    public DataSet<IPage> getPages() {
        return this.pageDataSet;
    }

}

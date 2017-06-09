package it.neef.tub.dima.ba.imwa.interfaces.parser;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.impl.configuration.Configuration;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IPreFilter;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for an InputParser to take care of
 * running the runDumpParser(), runPageviewParser(), applyPostFilters(),
 * populateDataHolder() methods in the right order. It also calls the
 * mergeDumpAndPageviewData() method and applies the PostFilters to the
 * page DataSet.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public abstract class AInputParser implements IInputParser {

    /**
     * DataSet of pages after applying the PostFilters
     *
     * @see IPostFilter
     */
    private DataSet<IPage> filteredIpageDataSet = null;

    /**
     * Calls all parsing related methods: runDumpParser(), runPageiewParser(), applyPostFilters(),
     * populateDataHolder()
     *
     * @throws Exception If an error occurs while parsing the data
     */
    public void run() throws Exception {
        this.runDumpParser();
        this.runPageviewParser();
        this.applyPostFilters();
        this.populateDataHolder();
    }

    interface testFunc extends Serializable {
        boolean filter();
    }

    /**
     * This method first calls mergeDumpAndPageviewData and then continues
     * to apply the PostFilters to the page DataSet.
     *
     * @throws Exception If an error occurs while processing the data.
     * @see Configuration
     */
    public void applyPostFilters() throws Exception {
        this.filteredIpageDataSet = this.mergeDumpAndPageviewData();
        /*filteredIpageDataSet = filteredIpageDataSet.filter(new FilterFunction<IPage>() {
            @Override
            public boolean filter(IPage iPage) throws Exception {
                return iPage.getNameSpace() == 0;
            }
        });
        */

        final List<IPostFilter> filterList = Framework.getInstance().getConfiguration().getPostFilters();
        for (final IPostFilter filter : filterList) {
            filteredIpageDataSet = filteredIpageDataSet.filter(new FilterFunction<IPage>() {
                @Override
                public boolean filter(IPage iPage) throws Exception {
                    return filter.filter(iPage);
                }
            });
        }

        /*
        //final List<IPreFilter> filterList = Framework.getInstance().getConfiguration().getPreFilters();
        List<IPostFilter> test = new ArrayList<>();
        IPostFilter filter = new IPostFilter() {

            @Override
            public boolean filter(IPage page) {
                return false;
            }
        };
        test.add(filter);
        final List<IPostFilter> filterList = test;



        class fooo implements testFunc {
            public boolean filter() {
                return false;
            }
        }

        class TestFilterFunction implements FilterFunction<IPage> {

            testFunc filter;

            class fooo2 implements testFunc {
                public boolean filter() {
                    return false;
                }
            }

            TestFilterFunction() {
                // WORKS with fooo2()
                // DOES NOT WORK with fooo()
                this.filter = new fooo();
            }
            @Override
            public boolean filter(IPage iPage) throws Exception {
                return filter.filter();
            }
        }

        //for (final IPostFilter filter : filterList) {
            filteredIpageDataSet = filteredIpageDataSet.filter(new TestFilterFunction());
        //}
        */
        /* THIS WORKS??? BUT IPostFilter does not?!?!?! FML
        for (final IPreFilter filter : filterList) {
            filteredIpageDataSet = filteredIpageDataSet.filter(new FilterFunction<IPage>() {
                @Override
                public boolean filter(IPage iPage) throws Exception {
                    return filter.filter(iPage.toString());
                }
            });
        }*/
        /*
        filteredIpageDataSet.filter(new RichFilterFunction<IPage>() {
            List<IPostFilter> filterList;
            @Override
            public void open(org.apache.flink.configuration.Configuration parameters) throws Exception {
                super.open(parameters);
                this.filterList = Framework.getInstance().getConfiguration().getPostFilters();
            }

            @Override
            public boolean filter(IPage iPage) throws Exception {
                for(IPostFilter filter : filterList) {
                    if(filter.filter(iPage) == false) {
                        return false;
                    }
                }
                return true;
            }
        });*/
    }

    /**
     * Getter for the parsed and filtered page DataSet.
     *
     * @return The filtered page DataSet.
     */
    public DataSet<IPage> getFilteredIpageDataSet() {
        return filteredIpageDataSet;
    }

    /**
     * Setter for the filtered page DataSet
     *
     * @param filteredIpageDataSet the new filtered page DataSet.
     */
    public void setFilteredIpageDataSet(DataSet<IPage> filteredIpageDataSet) {
        this.filteredIpageDataSet = filteredIpageDataSet;
    }
}

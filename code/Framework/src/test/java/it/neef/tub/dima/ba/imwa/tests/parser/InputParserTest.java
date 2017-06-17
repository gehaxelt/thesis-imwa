package it.neef.tub.dima.ba.imwa.tests.parser;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.impl.data.*;
import it.neef.tub.dima.ba.imwa.impl.factories.parser.DumpParserFactory;
import it.neef.tub.dima.ba.imwa.impl.parser.InputParser;
import it.neef.tub.dima.ba.imwa.impl.parser.PageviewParser;
import it.neef.tub.dima.ba.imwa.impl.parser.SkipDumpParser;
import it.neef.tub.dima.ba.imwa.interfaces.data.*;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IContributorFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IPageFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IPageviewFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IRevisionFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IDumpParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IPageviewParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IDumpParser;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IPageviewParser;
import it.neef.tub.dima.ba.imwa.tests.calc.RelevanceAggregatorTest;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.flink.api.java.DataSet;
import org.flinkspector.core.collection.ExpectedRecords;
import org.flinkspector.dataset.DataSetTestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 17.06.17.
 */
public class InputParserTest extends DataSetTestBase {

    Framework fw;
    static InputParserTest test;
    static IPage p1, p2, p3;
    static IRevision r1,r2,r3;
    static IPageview pv1, pv2, pv3, pv4;

    public static class TestParser extends SkipDumpParser {

        DataSet<IPage> pageDataSet;

        @Override
        public void parseDumpData() throws Exception {


            r1.setID(1);
            r1.setText("Text1");

            r2.setID(2);
            r2.setText("Text2");

            r3.setID(3);
            r3.setText("Text3");

            p1.setID(1);
            p1.setTitle("Page1");
            p1.setRevisions(asList(r1));

            p2.setID(2);
            p2.setTitle("Page2");
            p1.setRevisions(asList(r2));

            p3.setID(3);
            p3.setTitle("Page3");
            p1.setRevisions(asList(r3));

            this.pageDataSet = test.createTestDataSet(asList(p1,p2,p3));
        }

        @Override
        public DataSet<IPage> getPages() {
            return this.pageDataSet;
        }
    }

    public static class TestPageviewParser extends PageviewParser {
        DataSet<IPageview> pageviews;

        @Override
        public void parsePageviewData() throws Exception {

            pv1.setPageTitle("Page1");
            pv1.setProjectName("tw");
            pv1.setRequestCount(10);
            pv1.setRequestSize(100);

            pv2.setPageTitle("Page1");
            pv2.setProjectName("tw");
            pv2.setRequestCount(5);
            pv2.setRequestSize(200);


            pv3.setPageTitle("Page2");
            pv3.setProjectName("tw");
            pv3.setRequestCount(50);
            pv3.setRequestSize(300);


            pv4.setPageTitle("Page4");
            pv4.setProjectName("tw");
            pv4.setRequestCount(1);
            pv4.setRequestSize(10);

            this.pageviews = test.createTestDataSet(asList(pv1, pv2, pv3, pv4));
        }

        @Override
        public DataSet<IPageview> getPageviews() {
            return this.pageviews;
        }
    }

    public static class TestPage extends Page {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestPage)) return false;

            TestPage testPage = (TestPage) o;

            return getID() == testPage.getID() && getTitle().equals(testPage.getTitle());
        }
    }

    public static class TestRevision extends Revision {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestRevision)) return false;

            TestRevision testRevision = (TestRevision) o;

            return getID() == testRevision.getID() && getText().equals(testRevision.getText());
        }
    }

    public static class TestContributor extends Contributor {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestContributor)) return false;

            TestContributor testContributor = (TestContributor) o;

            return getID() == testContributor.getID() && getIdentifier() == testContributor.getIdentifier();
        }
    }

    public static class TestPageview extends Pageview {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestPageview)) return false;

            TestPageview testPageview = (TestPageview) o;

            return getPageTitle().equals(testPageview.getPageTitle()) && getProjectName().equals(testPageview.getProjectName()) && getRequestCount() == testPageview.getRequestCount();
        }
    }

    @Before
    public void setUp() throws Exception {
        fw = Framework.getInstance();
        test = this;


        p1 = new TestPage();
        p2 = new TestPage();
        p3 = new TestPage();


        r1 = new TestRevision();
        r2 = new TestRevision();
        r3 = new TestRevision();


        pv1 = new TestPageview();
        pv2 = new TestPageview();
        pv3 = new TestPageview();
        pv4 = new TestPageview();

        fw.getConfiguration().setDumpParserFactory(new IDumpParserFactory() {
            @Override
            public IDumpParser newDumpParser() {
                return new TestParser();
            }
        });
        fw.getConfiguration().setPageviewParserFactory(new IPageviewParserFactory() {
            @Override
            public IPageviewParser<? extends IPageview> newPageviewParser() {
                return new TestPageviewParser();
            }
        });
        fw.getConfiguration().setPageFactory(new IPageFactory() {
            @Override
            public IPage<IRevision, IPageview> newPage() {
                return new TestPage();
            }
        });
        fw.getConfiguration().setRevisionFactory(new IRevisionFactory() {
            @Override
            public IRevision newRevision() {
                return new TestRevision();
            }
        });
        fw.getConfiguration().setContributorFactory(new IContributorFactory() {
            @Override
            public IContributor newContributor() {
                return new TestContributor();
            }
        });
        fw.getConfiguration().setPageviewFactory(new IPageviewFactory() {
            @Override
            public IPageview newPageview() {
                return new TestPageview();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void mergeDumpAndPageviewDataNoPageviews() throws Exception {
        InputParser inp = new InputParser();
        inp.runDumpParser();
        assertDataSet(inp.mergeDumpAndPageviewData(), ExpectedRecords.create(asList(p1,p2, p3)));
    }


    /*
    org.apache.flink.api.common.functions.InvalidTypesException: Input mismatch: POJO type 'it.neef.tub.dima.ba.imwa.tests.parser.InputParserTest.TestPage' expected but was 'it.neef.tub.dima.ba.imwa.interfaces.data.IPage'.

    @Test
    public void mergeDumpAndPageviewData() throws Exception {
        fw.getConfiguration().setPageviewDumpPath("testParser");
        fw.getConfiguration().setPageviewDumpShortTag("tw");
        InputParser inp = new InputParser();
        IPageview pv12 = new TestPageview();
        pv12.setPageTitle("Page1");
        pv12.setProjectName("tw");
        pv12.setRequestCount(15);
        pv12.setRequestCount(300);
        p1.setPageview(pv12);
        p2.setPageview(pv3);
        inp.runDumpParser();
        inp.runPageviewParser();
        ExpectedRecords<IPage> exp = ExpectedRecords.create(asList(p1,p2, p3));
        assertDataSet((DataSet<IPage>)inp.mergeDumpAndPageviewData(), exp);
    }
    */
    
    @Test
    public void populateDataHolder() throws Exception {
        InputParser inp = new InputParser();
        IDataHolder dh = fw.getConfiguration().getDataHolder();
        inp.runDumpParser();
        inp.applyPostFilters();
        inp.populateDataHolder();
        assertDataSet(dh.getPages(), ExpectedRecords.create(asList(p1,p2,p3)));
        //assertDataSet(dh.getRevisions(), ExpectedRecords.create(asList(r1,r2,r3))); // org.apache.flink.api.common.functions.InvalidTypesException: The return type of function 'getRevisions(InputParser.java:91)' could not be determined automatically, due to type erasure. You can give type information hints by using the returns(...) method on the result of the transformation call, or by letting your function implement the 'ResultTypeQueryable' interface.

    }

}
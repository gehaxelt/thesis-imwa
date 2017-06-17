package it.neef.tub.dima.ba.imwa.tests.data;

import it.neef.tub.dima.ba.imwa.impl.data.Contributor;
import it.neef.tub.dima.ba.imwa.impl.data.DataHolder;
import it.neef.tub.dima.ba.imwa.impl.data.Page;
import it.neef.tub.dima.ba.imwa.impl.data.Revision;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import org.apache.flink.api.java.DataSet;
import org.flinkspector.core.collection.ExpectedRecords;
import org.flinkspector.dataset.DataSetTestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 17.06.17.
 */
public class DataHolderTest extends DataSetTestBase {

    IPage p1,p2, p3;
    IRevision r1,r2,r3;
    IContributor c1,c2,c3;
    //DataHolder dh;
    DataSet<IPage> pages;
    DataSet<IRevision> revisions;
    DataSet<IContributor> contributors;

    public static class TestPage extends Page{
        @Override
        public boolean equals(Object o) {
            Page testPage = (Page)o;

            return getID() == testPage.getID() && getTitle().equals(testPage.getTitle());
        }
    }

    public static class TestRevision extends Revision {
        @Override
        public boolean equals(Object o) {
            Revision testPage = (Revision)o;

            return getID() == testPage.getID() && getText().equals(testPage.getText());
        }
    }

    public static class TestContributor extends Contributor {
        @Override
        public boolean equals(Object o) {
            Contributor testPage = (Contributor)o;

            return getID() == testPage.getID() && getIdentifier() == testPage.getIdentifier();
        }
    }

    @Before
    public void setUp() throws Exception {
        p1 = new TestPage();
        p2 = new TestPage();
        p3 = new TestPage();
        r1 = new TestRevision();
        r2 = new TestRevision();
        r3 = new TestRevision();
        c1 = new TestContributor();
        c2 = new TestContributor();
        c3 = new TestContributor();

        p1.setID(1);
        p1.setTitle("Page1");
        p1.setNameSpace(0);

        p2.setID(2);
        p2.setTitle("Page2");
        p2.setNameSpace(0);

        p3.setID(3);
        p3.setTitle("Page3");
        p3.setNameSpace(0);


        r1.setID(1);
        r1.setText("Revision1");

        r2.setID(2);
        r2.setText("Revision2");

        r3.setID(3);
        r3.setText("Revision3");


        c1.setID(1);
        c1.setUsername("User1");

        c2.setID(2);
        c2.setUsername("User2");

        c3.setID(3);
        c3.setIP("10.13.37");

    }

    @After
    public void tearDown() throws Exception {
    }

    /*
    @Test
    public void addPage() throws Exception {
        //Unfortunately, we cannot test with different execution environments!
        //So no tests for add-methods
    }*/

    @Test
    public void removePageByID() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setPages(newPages());
        dh.removePageByID(p2.getID());

        assertDataSet(dh.getPages(), ExpectedRecords.create(asList(p1)));
    }

    @Test
    public void getPageByID() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setPages(newPages());
        Page result = (Page) dh.getPageByID(p2.getID());

        assertDataSet(createTestDataSet(asList(p2)), ExpectedRecords.create(asList(result)));
    }


    @Test
    public void removeRevisionByID() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setRevisions(newRevisions());
        dh.removeRevisionByID(r2.getID());

        assertDataSet(dh.getRevisions(), ExpectedRecords.create(asList(r1)));
    }

    @Test
    public void getRevisionByID() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setRevisions(newRevisions());
        TestRevision result = (TestRevision) dh.getRevisionByID(r2.getID());

        assertDataSet(createTestDataSet(asList(r2)), ExpectedRecords.create(asList(result)));
    }


    @Test
    public void removeContributorByID() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setContributors(newContributors());
        dh.removeContributorByID(c2.getID());

        assertDataSet(dh.getContributors(), ExpectedRecords.create(asList(c1)));
    }

    @Test
    public void removeContributorByUsername() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setContributors(newContributors());
        dh.removeContributorByUsername(c2.getUsername());

        assertDataSet(dh.getContributors(), ExpectedRecords.create(asList(c1)));
    }

    @Test
    public void getContributorByID() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setContributors(newContributors());
        TestContributor result = (TestContributor) dh.getContributorByID(c2.getID());

        assertDataSet(createTestDataSet(asList(c2)), ExpectedRecords.create(asList(result)));
    }

    @Test
    public void getContributorByUsername() throws Exception {
        DataHolder dh = new DataHolder();
        dh.setContributors(newContributors());
        TestContributor result = (TestContributor) dh.getContributorByUsername(c1.getUsername());

        assertDataSet(createTestDataSet(asList(c1)), ExpectedRecords.create(asList(result)));
    }

    private DataSet<IContributor> newContributors() {
        return createTestDataSet(asList(c1,c2));
    }
    private DataSet<IRevision> newRevisions() {
        return createTestDataSet(asList(r1,r2));
    }
    private DataSet<IPage> newPages() {
        return createTestDataSet(asList(p1,p2));
    }
}
package it.neef.tub.dima.ba.imwa.tests.data;

import it.neef.tub.dima.ba.imwa.impl.data.DoubleDifference;
import it.neef.tub.dima.ba.imwa.impl.data.Revision;
import it.neef.tub.dima.ba.imwa.impl.diff.LengthDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
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
public class LengthDifferTest extends DataSetTestBase {

    LengthDiffer ld;

    @Before
    public void setUp() throws Exception {
        ld = new LengthDiffer();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calculateDiff() throws Exception {
        IRevision r1, r2;
        r1 = null;
        r2 = new Revision();

        r2.setText("12345");

        DoubleDifference dd = (DoubleDifference) ld.calculateDiff(null, r2);

        assertDataSet(createTestDataSet(asList(5.0)), ExpectedRecords.create(dd.getDifference()));
    }

    @Test
    public void calculateDiff2() throws Exception {
        IRevision r1, r2;
        r1 = new Revision();
        r2 = new Revision();

        r1.setText("");
        r2.setText("12345");

        DoubleDifference dd = (DoubleDifference) ld.calculateDiff(r1, r2);

        assertDataSet(createTestDataSet(asList(5.0)), ExpectedRecords.create(dd.getDifference()));
    }

    @Test
    public void calculateDiff3() throws Exception {
        IRevision r1, r2;
        r1 = new Revision();
        r2 = new Revision();

        r1.setText("123");
        r2.setText("12345");

        DoubleDifference dd = (DoubleDifference) ld.calculateDiff(r1, r2);

        assertDataSet(createTestDataSet(asList(2.0)), ExpectedRecords.create(dd.getDifference()));
    }


}
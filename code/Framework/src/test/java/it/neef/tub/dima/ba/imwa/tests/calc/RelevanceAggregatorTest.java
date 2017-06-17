package it.neef.tub.dima.ba.imwa.tests.calc;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.impl.calc.RelevanceAggregator;
import it.neef.tub.dima.ba.imwa.impl.data.Contributor;
import it.neef.tub.dima.ba.imwa.impl.data.ContributorRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.calc.IRelevanceAggregator;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDataType;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.runtime.io.network.util.TestSubpartitionConsumer;
import org.flinkspector.core.collection.ExpectedRecords;
import org.flinkspector.core.collection.MatcherBuilder;
import org.flinkspector.core.quantify.MatchRecords;
import org.flinkspector.dataset.DataSetTestBase;
//import org.flinkspector.matcher.ListMatcher;
import org.flinkspector.matcher.ListMatcher;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.collection.immutable.List;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class RelevanceAggregatorTest extends DataSetTestBase {

    TestScore cs1, cs2, cs3, cs4, cs5;
    Contributor c1, c2, c3;

    DataSet<TestScore> left, right;

    IRelevanceAggregator ag;

    public static class TestScore implements IRelevanceScore {

        IDataType object;
        Double score;

        @Override
        public Double getRelevanceScore() {
            return score;
        }

        @Override
        public void setRelevanceScore(Double score) {
            this.score = score;
        }

        @Override
        public IDataType getObject() {
            return object;
        }

        @Override
        public void setObject(IDataType object) {
            this.object = object;
        }

        @Override
        public long getIdentifier() {
            return object.getIdentifier();
        }

        @Override
        public String toString() {
            return "TestScore{" +
                    "object=" + object +
                    ", score=" + score +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestScore)) return false;

            TestScore testScore = (TestScore) o;
            //return true;

            return getIdentifier() == testScore.getIdentifier() && score.doubleValue() == testScore.score.doubleValue();
            //if (object != null ? !(object.getIdentifier() == testScore.object.getIdentifier()) : testScore.object != null) return false;
            //return score != null ? score != testScore.score : testScore.score == null;
        }
    }

    @Before
    public void setUp() throws Exception {
        ag = new RelevanceAggregator(); //code/Framework.getInstance().getConfiguration().getRelevanceAggregator();

        c1 = new Contributor();
        c1.setID(1);
        c1.setUsername("User1");

        c2 = new Contributor();
        c2.setID(2);
        c2.setUsername("User2");

        c3 = new Contributor();
        c3.setID(3);
        c3.setIP("127.0.0.1");

        cs1 = new TestScore();
        cs1.setRelevanceScore((Double)1.0);
        cs1.setObject(c1);

        cs2 = new TestScore();
        cs2.setRelevanceScore((Double)0.0);
        cs2.setObject(c2);

        cs3 =  new TestScore();
        cs3.setRelevanceScore((Double)5.0);
        cs3.setObject(c3);

        cs4 = new TestScore();
        cs4.setRelevanceScore((Double)0.5);
        cs4.setObject(c1);

        cs5 = new TestScore();
        cs5.setRelevanceScore((Double)1.0);
        cs5.setObject(c2);

        //                              1.0  0.0  5.0
        left = createTestDataSet(asList(cs1, cs2, cs3));
        //                              0.5   1.0
        right = createTestDataSet(asList(cs4, cs5));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() throws Exception {
        TestScore e1, e2;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)1.5);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)1.0);
        e2.setObject(c2);


        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.add(left, right);
        assertDataSet(result, expected);

    }

    @Test
    public void sub() throws Exception {
        TestScore e1, e2;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)0.5);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)0.0-1.0);
        e2.setObject(c2);


        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.sub(left, right);
        assertDataSet(result, expected);
    }

    @Test
    public void mul() throws Exception {
        TestScore e1, e2;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)0.5);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)0.0);
        e2.setObject(c2);


        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.mul(left, right);
        assertDataSet(result, expected);

    }

    @Test
    public void div() throws Exception {
        TestScore e1, e2;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)2.0);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)0.0);
        e2.setObject(c2);


        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.div(left, right);
        assertDataSet(result, expected);

    }

    @Test
    public void addValue() throws Exception {
        TestScore e1, e2, e3;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)6.0);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)5.0);
        e2.setObject(c2);


        e3 = new TestScore();
        e3.setRelevanceScore((Double)10.0);
        e3.setObject(c3);

        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2,e3));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.addValue(left, (Double)5.0);
        assertDataSet(result, expected);
    }

    @Test
    public void subValue() throws Exception {
        TestScore e1, e2, e3;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)0.0-4.0);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)(0.0-5.0));
        e2.setObject(c2);


        e3 = new TestScore();
        e3.setRelevanceScore((Double)0.0);
        e3.setObject(c3);

        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2,e3));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.subValue(left, (Double)5.0);
        assertDataSet(result, expected);
    }

    @Test
    public void mulValue() throws Exception {
        TestScore e1, e2, e3;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)5.0);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)0.0);
        e2.setObject(c2);


        e3 = new TestScore();
        e3.setRelevanceScore((Double)25.0);
        e3.setObject(c3);

        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2,e3));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.mulValue(left, (Double)5.0);
        assertDataSet(result, expected);
    }

    @Test
    public void divValue() throws Exception {
        TestScore e1, e2, e3;
        e1 = new TestScore();
        e1.setRelevanceScore((Double)0.2);
        e1.setObject(c1);

        e2 = new TestScore();
        e2.setRelevanceScore((Double)0.0);
        e2.setObject(c2);


        e3 = new TestScore();
        e3.setRelevanceScore((Double)1.0);
        e3.setObject(c3);

        ExpectedRecords<TestScore> expected = ExpectedRecords.create(asList(e1,e2,e3));
        DataSet<TestScore> result = (DataSet<TestScore>) ag.divValue(left, (Double)5.0);
        assertDataSet(result, expected);
    }

    @Test
    public void sum() throws Exception {
        double result = ag.sum(left);
        assertDataSet(createTestDataSet(asList(result)), ExpectedRecords.create(6.0));
    }

    @Test
    public void min() throws Exception {
        double result = ag.min(left);
        assertDataSet(createTestDataSet(asList(result)), ExpectedRecords.create(0.0));
    }

    @Test
    public void max() throws Exception {
        double result = ag.max(left);
        assertDataSet(createTestDataSet(asList(result)), ExpectedRecords.create(5.0));
    }

    @Test
    public void getByIdentifier() throws Exception {
        // ID of cs3
        IDataType c = ag.getByIdentifier(left, 2159);
        assertDataSet(createTestDataSet(asList(c)), ExpectedRecords.create(cs3));
    }

}
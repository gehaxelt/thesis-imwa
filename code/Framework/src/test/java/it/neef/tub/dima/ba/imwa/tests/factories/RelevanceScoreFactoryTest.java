package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.ContributorRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.data.DifferenceRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.data.PageRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.data.RevisionRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.factories.data.RelevanceScoreFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class RelevanceScoreFactoryTest {

    RelevanceScoreFactory rsf;
    @Before
    public void setUp() throws Exception {
        rsf = new RelevanceScoreFactory();
    }

    @Test
    public void newPageRelevanceScore() throws Exception {
        assertEquals(PageRelevanceScore.class, rsf.newPageRelevanceScore().getClass());
    }

    @Test
    public void newRevisionRelevanceScore() throws Exception {
        assertEquals(RevisionRelevanceScore.class, rsf.newRevisionRelevanceScore().getClass());
    }

    @Test
    public void newContributorRelevanceScore() throws Exception {
        assertEquals(ContributorRelevanceScore.class, rsf.newContributorRelevanceScore().getClass());
    }

    @Test
    public void newDifferenceRelevanceScore() throws Exception {
        assertEquals(DifferenceRelevanceScore.class, rsf.newDifferenceRelevanceScore().getClass());
    }

}
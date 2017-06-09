package it.neef.tub.dima.ba.imwa.impl.factories.data;

import it.neef.tub.dima.ba.imwa.impl.data.ContributorRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.data.DifferenceRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.data.PageRelevanceScore;
import it.neef.tub.dima.ba.imwa.impl.data.RevisionRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IRelevanceScoreFactory;
import it.neef.tub.dima.ba.imwa.interfaces.data.*;

/**
 * Factory for Page-/Revision-/Contributor- and DifferenceRelevanceScores.
 *
 * @see IRelevanceScore
 * Created by gehaxelt on 25.10.16.
 */
public class RelevanceScoreFactory implements IRelevanceScoreFactory<IPage, IRevision, IContributor, IDifference, Double> {
    @Override
    public IRelevanceScore<IPage, Double> newPageRelevanceScore() {
        return new PageRelevanceScore();
    }

    @Override
    public IRelevanceScore<IRevision, Double> newRevisionRelevanceScore() {
        return new RevisionRelevanceScore();
    }

    @Override
    public IRelevanceScore<IContributor, Double> newContributorRelevanceScore() {
        return new ContributorRelevanceScore();
    }

    @Override
    public IRelevanceScore<IDifference, Double> newDifferenceRelevanceScore() {
        return new DifferenceRelevanceScore();
    }
}

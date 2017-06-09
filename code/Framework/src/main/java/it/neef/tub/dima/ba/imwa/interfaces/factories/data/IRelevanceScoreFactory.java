package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.*;

/**
 * Interface for a factory of RevelanceScores.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public interface IRelevanceScoreFactory<P extends IPage, R extends IRevision, C extends IContributor, D extends IDifference, S extends Double> {

    /**
     * Should instantiate a new PageRelevanceScore object.
     *
     * @return a new PageRelevanceScore
     * @see IRelevanceScore
     */
    IRelevanceScore<P, Double> newPageRelevanceScore();

    /**
     * Should instantiate a new RevisionRelevanceScore object.
     *
     * @return a new RevisionRelevanceScore
     * @see IRelevanceScore
     */
    IRelevanceScore<R, Double> newRevisionRelevanceScore();

    /**
     * Should instantiate a new ContributorRelevanceScore object.
     *
     * @return a new ContributorRelevanceScore
     * @see IRelevanceScore
     */
    IRelevanceScore<C, Double> newContributorRelevanceScore();

    /**
     * Should instantiate a new DifferenceRelevanceScore object.
     *
     * @return a new DifferenceRelevanceScore
     * @see IRelevanceScore
     */
    IRelevanceScore<D, Double> newDifferenceRelevanceScore();
}

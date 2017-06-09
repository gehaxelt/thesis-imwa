package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;

/**
 * Class for RelevanceScores of Contributors.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public class ContributorRelevanceScore implements IRelevanceScore<IContributor, Double> {

    /**
     * The score for this object.
     */
    private Double relevanceScore;

    /**
     * The rated contributor object.
     */
    private IContributor object;

    @Override
    public Double getRelevanceScore() {
        return this.relevanceScore;
    }

    @Override
    public void setRelevanceScore(Double score) {
        this.relevanceScore = score;
    }

    @Override
    public IContributor getObject() {
        return this.object;
    }

    @Override
    public void setObject(IContributor object) {
        this.object = object;
    }

    @Override
    public long getIdentifier() {
        return this.object.getIdentifier();
    }

    @Override
    public String toString() {
        return this.object.getUsername() + ", " + String.valueOf(relevanceScore);
    }
}

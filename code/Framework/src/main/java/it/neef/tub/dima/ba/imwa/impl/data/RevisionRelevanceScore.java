package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;

/**
 * RelevanceScore class for Revisions.
 *
 * @see IRevision
 * Created by gehaxelt on 25.10.16.
 */
public class RevisionRelevanceScore implements IRelevanceScore<IRevision, Double> {

    /**
     * The score for this object.
     */
    private Double relevanceScore;

    /**
     * The rated revision.
     */
    private IRevision object;

    @Override
    public Double getRelevanceScore() {
        return this.relevanceScore;
    }

    @Override
    public void setRelevanceScore(Double score) {
        this.relevanceScore = score;
    }

    @Override
    public IRevision getObject() {
        return this.object;
    }

    @Override
    public void setObject(IRevision object) {
        this.object = object;
    }

    @Override
    public long getIdentifier() {
        return this.object.getIdentifier();
    }
}

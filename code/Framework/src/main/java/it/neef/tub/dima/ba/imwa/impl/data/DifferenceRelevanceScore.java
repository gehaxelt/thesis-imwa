package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;

/**
 * RelevanceScore class for Difference objects.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public class DifferenceRelevanceScore implements IRelevanceScore<IDifference, Double> {

    /**
     * The score for this object.
     */
    private Double relevanceScore;

    /**
     * The rated difference object.
     */
    private IDifference object;

    @Override
    public Double getRelevanceScore() {
        return this.relevanceScore;
    }

    @Override
    public void setRelevanceScore(Double score) {
        this.relevanceScore = score;
    }

    @Override
    public IDifference getObject() {
        return this.object;
    }

    @Override
    public void setObject(IDifference object) {
        this.object = object;
    }

    @Override
    public long getIdentifier() {
        return this.object.getIdentifier();
    }
}

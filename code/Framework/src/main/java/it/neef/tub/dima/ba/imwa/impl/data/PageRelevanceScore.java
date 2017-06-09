package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;

/**
 * RelevanceScore class for Pages.
 *
 * @see IPage
 * Created by gehaxelt on 25.10.16.
 */
public class PageRelevanceScore implements IRelevanceScore<IPage, Double> {

    /**
     * The score for this object.
     */
    private Double relevanceScore;

    /**
     * The rated page object.
     */
    private IPage object;

    @Override
    public Double getRelevanceScore() {
        return this.relevanceScore;
    }

    @Override
    public void setRelevanceScore(Double score) {
        this.relevanceScore = score;
    }

    @Override
    public IPage getObject() {
        return this.object;
    }

    @Override
    public void setObject(IPage object) {
        this.object = object;
    }

    @Override
    public long getIdentifier() {
        return this.object.getIdentifier();
    }
}

package it.neef.tub.dima.ba.imwa.interfaces.filters.post;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;

/**
 * Abstract class for a PostFilter class which takes care of applying a
 * PostFilter to a page.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public abstract class APostFilter<F extends IPostFilter, P extends IPage> implements IPostFilter<P> {

    /**
     * PostFilter which will be applied to a page.
     */
    private IPostFilter filterClass;

    /**
     * Constructor which sets the filterClass variable.
     *
     * @param filterClass the PostFilter object to use.
     */
    public APostFilter(F filterClass) {
        this.filterClass = filterClass;
    }

    /**
     * Applies the filterClass to a page.
     *
     * @param page the page to check for its validity.
     * @return true if the page passes the filter and is accepted. false otherwise.
     * @see IPostFilter
     */
    public boolean filter(P page) {
        return this.filterClass.filter(page);
    }
}

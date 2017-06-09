package it.neef.tub.dima.ba.imwa.interfaces.factories.filters.post;

import it.neef.tub.dima.ba.imwa.interfaces.filters.post.APostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;

import java.io.Serializable;

/**
 * Interface for a factory of PostFilters.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface ICustomPostFilterFactory<F extends IPostFilter, A extends APostFilter> extends Serializable {

    /**
     * Should instantiate a new APostFilter object based on the PostFilter instance of type F.
     *
     * @param filter the PostFilter object to use for filtering.
     * @return new APostFilter instance which will use the given filter to filter pages.
     * @see IPostFilter
     * @see APostFilter
     */
    A newICustomPostFilter(F filter);
}

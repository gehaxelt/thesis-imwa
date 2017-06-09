package it.neef.tub.dima.ba.imwa.impl.factories.filters.post;

import it.neef.tub.dima.ba.imwa.impl.filters.post.CustomPostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.post.ICustomPostFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.APostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;

/**
 * Factory for CustomPostFilters.
 *
 * @see APostFilter
 * Created by gehaxelt on 08.10.16.
 */
public class CustomPostFilterFactory implements ICustomPostFilterFactory<IPostFilter, APostFilter> {
    @Override
    public APostFilter newICustomPostFilter(IPostFilter filter) {
        return new CustomPostFilter(filter);
    }
}

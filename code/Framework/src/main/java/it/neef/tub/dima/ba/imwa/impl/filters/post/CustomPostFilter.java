package it.neef.tub.dima.ba.imwa.impl.filters.post;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.APostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;

/**
 * Customizable PostFilter which runs a given IPostFilter class on the XML page strings.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class CustomPostFilter extends APostFilter<IPostFilter<IPage>, IPage> {
    public CustomPostFilter(IPostFilter<IPage> filterClass) {
        super(filterClass);
    }
}

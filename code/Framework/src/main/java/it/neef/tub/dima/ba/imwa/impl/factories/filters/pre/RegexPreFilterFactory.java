package it.neef.tub.dima.ba.imwa.impl.factories.filters.pre;

import it.neef.tub.dima.ba.imwa.impl.filters.pre.RegexPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IRegexPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IRegexPreFilter;

/**
 * Factory for RegexPreFilter.
 *
 * @see IRegexPreFilter
 * Created by gehaxelt on 08.10.16.
 */
public class RegexPreFilterFactory implements IRegexPreFilterFactory<IRegexPreFilter> {
    @Override
    public IRegexPreFilter newIRegexPreFilter(String regex) {
        IRegexPreFilter filter = new RegexPreFilter();
        filter.setRegex(regex);
        return filter;
    }
}

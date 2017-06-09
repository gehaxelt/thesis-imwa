package it.neef.tub.dima.ba.imwa.impl.factories.filters.pre;

import it.neef.tub.dima.ba.imwa.impl.filters.pre.XpathPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IXpathPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXpathPreFilter;

/**
 * Factory for XpathPreFilter objects.
 *
 * @see IXpathPreFilter
 * Created by gehaxelt on 08.10.16.
 */
public class XpathPreFilterFactory implements IXpathPreFilterFactory<IXpathPreFilter> {

    @Override
    public IXpathPreFilter newIXpathPreFilter(String xpath) {
        IXpathPreFilter filter = new XpathPreFilter();
        filter.setXpath(xpath);
        return filter;
    }
}

package it.neef.tub.dima.ba.imwa.impl.factories.filters.pre;

import it.neef.tub.dima.ba.imwa.impl.filters.pre.XqueryPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IXqueryPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXqueryPreFilter;

/**
 * Factory for XqueryPreFilter objects.
 *
 * @see IXqueryPreFilter
 * Created by gehaxelt on 08.10.16.
 */
public class XqueryPreFilterFactory implements IXqueryPreFilterFactory<IXqueryPreFilter> {
    @Override
    public IXqueryPreFilter newXqueryPreFilter(String xquery) {
        IXqueryPreFilter filter = new XqueryPreFilter();
        filter.setXquery(xquery);
        return filter;
    }
}

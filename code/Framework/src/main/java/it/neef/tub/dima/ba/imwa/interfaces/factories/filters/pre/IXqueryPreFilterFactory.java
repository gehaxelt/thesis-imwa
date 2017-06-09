package it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre;

import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXqueryPreFilter;

import java.io.Serializable;

/**
 * Interface for a factory of XqueryPreFilter objects.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IXqueryPreFilterFactory<F extends IXqueryPreFilter> extends Serializable {

    /**
     * Should instantiate a new XqueryPreFilter with the given Xquery string.
     *
     * @param xquery the Xquery filter string to use.
     * @return new XqueryPreFilter instance
     * @see IXqueryPreFilter
     */
    F newXqueryPreFilter(String xquery);
}

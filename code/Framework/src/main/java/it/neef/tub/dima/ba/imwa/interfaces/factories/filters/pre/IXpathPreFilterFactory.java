package it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre;

import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXpathPreFilter;

import java.io.Serializable;

/**
 * Interface for a factory of XpathPreFilter objects.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IXpathPreFilterFactory<F extends IXpathPreFilter> extends Serializable {

    /**
     * Should instantiate a new XpathPreFilter with the given Xpath string.
     *
     * @param xpath the Xpath filter string to use.
     * @return new XpathPreFilter instance
     * @see IXpathPreFilter
     */
    F newIXpathPreFilter(String xpath);
}

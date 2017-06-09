package it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre;

import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IRegexPreFilter;

import java.io.Serializable;

/**
 * Interface for a factory of RegexPreFilter objects.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IRegexPreFilterFactory<F extends IRegexPreFilter> extends Serializable {

    /**
     * Should instantiate a new RegexPreFilter with the given regular expression string.
     *
     * @param regex the regular expression filter string to use.
     * @return new RegexPreFilter instance
     * @see IRegexPreFilter
     */
    F newIRegexPreFilter(String regex);
}
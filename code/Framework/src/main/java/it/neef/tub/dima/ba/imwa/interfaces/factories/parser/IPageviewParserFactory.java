package it.neef.tub.dima.ba.imwa.interfaces.factories.parser;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IPageviewParser;

import java.io.Serializable;

/**
 * Interface for a factory of PageViewParsers
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IPageviewParserFactory<T extends IPageviewParser<? extends IPageview>> extends Serializable {
    /**
     * Should instantiate a new PageviewParser object.
     *
     * @return the new PageviewParser object of type T
     * @see IPageviewParser
     */
    T newPageviewParser();
}

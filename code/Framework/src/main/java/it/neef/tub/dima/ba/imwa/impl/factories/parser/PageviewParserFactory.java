package it.neef.tub.dima.ba.imwa.impl.factories.parser;

import it.neef.tub.dima.ba.imwa.impl.parser.PageviewParser;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IPageviewParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IPageviewParser;

/**
 * Factory for PageviewParser objects.
 *
 * @see IPageviewParser
 * Created by gehaxelt on 09.10.16.
 */
public class PageviewParserFactory implements IPageviewParserFactory<IPageviewParser<IPageview>> {

    @Override
    public IPageviewParser<IPageview> newPageviewParser() {
        return new PageviewParser();
    }
}

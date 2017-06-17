package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.data.PageviewFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.parser.PageviewParserFactory;
import it.neef.tub.dima.ba.imwa.impl.parser.PageviewParser;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class PageviewParserFactoryTest {

    @Test
    public void newPageviewParser() throws Exception {
        PageviewParserFactory pf = new PageviewParserFactory();
        assertEquals(PageviewParser.class, pf.newPageviewParser().getClass());
    }

}
package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.filters.pre.XpathPreFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.filters.pre.XpathPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXpathPreFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class XpathPreFilterFactoryTest {
    @Test
    public void newIXpathPreFilter() throws Exception {
        String query = "/page[ns=\"0\"]";
        XpathPreFilterFactory xpff = new XpathPreFilterFactory();
        IXpathPreFilter xpf = xpff.newIXpathPreFilter(query);

        assertEquals(XpathPreFilter.class, xpf.getClass());

        assertSame(query, xpf.getXpath());
    }

}
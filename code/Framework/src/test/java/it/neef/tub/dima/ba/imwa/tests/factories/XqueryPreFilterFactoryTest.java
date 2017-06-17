package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.filters.pre.XqueryPreFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.filters.pre.XqueryPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXqueryPreFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class XqueryPreFilterFactoryTest {
    @Test
    public void newXqueryPreFilter() throws Exception {
        String query = "/page[ns=\"0\"]";
        XqueryPreFilterFactory xpff = new XqueryPreFilterFactory();
        IXqueryPreFilter xpf = xpff.newXqueryPreFilter(query);

        assertEquals(XqueryPreFilter.class, xpf.getClass());

        assertSame(query, xpf.getXquery());
    }

}
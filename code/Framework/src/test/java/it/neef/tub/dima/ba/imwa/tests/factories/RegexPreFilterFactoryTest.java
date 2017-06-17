package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.filters.pre.RegexPreFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.filters.pre.RegexPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IRegexPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IRegexPreFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class RegexPreFilterFactoryTest {
    @Test
    public void newIRegexPreFilter() throws Exception {
        String filter = "(?is).*<ns>0</ns>.*";
        IRegexPreFilterFactory rrff = new RegexPreFilterFactory();
        IRegexPreFilter rpf = rrff.newIRegexPreFilter(filter);

        assertEquals(RegexPreFilter.class, rpf.getClass());
        assertSame(filter, rpf.getRegex());
    }

}
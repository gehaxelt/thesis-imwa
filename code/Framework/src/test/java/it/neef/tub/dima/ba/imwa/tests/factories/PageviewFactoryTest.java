package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.Pageview;
import it.neef.tub.dima.ba.imwa.impl.factories.data.PageviewFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class PageviewFactoryTest {
    @Test
    public void newPageview() throws Exception {
        PageviewFactory pf = new PageviewFactory();
        assertEquals(Pageview.class, pf.newPageview().getClass());
    }

}
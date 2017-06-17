package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.Page;
import it.neef.tub.dima.ba.imwa.impl.factories.data.PageFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class PageFactoryTest {
    @Test
    public void newPage() throws Exception {
        PageFactory pf = new PageFactory();
        assertEquals(Page.class, pf.newPage().getClass());
    }

}
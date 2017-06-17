package it.neef.tub.dima.ba.imwa.tests.filters;

import it.neef.tub.dima.ba.imwa.impl.data.Page;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class CustomPostFilterTest {

    IPostFilter filter;
    IPage page0, page1;

    @Before
    public void setUp() throws Exception {
        page0 = new Page();
        page0.setNameSpace(0);

        page1 = new Page();
        page1.setNameSpace(1);

        filter = new IPostFilter() {
            @Override
            public boolean filter(IPage page) {
                return page.getNameSpace() == 0;
            }
        };
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void filterTrue() throws Exception {
        assertTrue(filter.filter(page0));
    }

    @Test
    public void filterFalse() throws Exception {
        assertFalse(filter.filter(page1));
    }
}
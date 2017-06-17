package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.filters.post.CustomPostFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.filters.post.CustomPostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.post.ICustomPostFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.APostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class CustomPostFilterFactoryTest {
    @Test
    public void newICustomPostFilter() throws Exception {
        ICustomPostFilterFactory cpf = new CustomPostFilterFactory();
        APostFilter apf = cpf.newICustomPostFilter(new IPostFilter() {
            @Override
            public boolean filter(IPage page) {
                return page.getNameSpace() == 0;
            }
        });

        assertEquals(CustomPostFilter.class, apf.getClass());
    }

}
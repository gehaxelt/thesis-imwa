package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.DataHolder;
import it.neef.tub.dima.ba.imwa.impl.factories.data.DataHolderFactory;
import org.junit.Test;

import javax.xml.crypto.Data;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class DataHolderFactoryTest {
    @Test
    public void newDataHolder() throws Exception {
        DataHolderFactory dhf = new DataHolderFactory();
        assertEquals(DataHolder.class, dhf.newDataHolder().getClass());
    }

}
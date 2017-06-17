package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.diff.LengthDiffer;
import it.neef.tub.dima.ba.imwa.impl.factories.data.DifferenceFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.diff.LengthDifferFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class LengthDifferFactoryTest {
    @Test
    public void newDiffer() throws Exception {
        LengthDifferFactory pf = new LengthDifferFactory();
        assertEquals(LengthDiffer.class, pf.newDiffer().getClass());
    }

}
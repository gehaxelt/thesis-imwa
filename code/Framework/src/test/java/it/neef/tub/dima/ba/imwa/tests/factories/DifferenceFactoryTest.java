package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.DoubleDifference;
import it.neef.tub.dima.ba.imwa.impl.diff.LengthDiffer;
import it.neef.tub.dima.ba.imwa.impl.factories.data.DifferenceFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class DifferenceFactoryTest {
    @Test
    public void newDifference() throws Exception {
        DifferenceFactory df = new DifferenceFactory();
        assertEquals(DoubleDifference.class, df.newDifference().getClass());
    }

}
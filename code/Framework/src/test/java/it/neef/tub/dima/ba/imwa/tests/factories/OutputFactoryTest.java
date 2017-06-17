package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.output.OutputFactory;
import it.neef.tub.dima.ba.imwa.impl.output.ConsoleOutput;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class OutputFactoryTest {
    @Test
    public void newOutput() throws Exception {
        OutputFactory of = new OutputFactory();
        assertEquals(ConsoleOutput.class, of.newOutput().getClass());
    }

}
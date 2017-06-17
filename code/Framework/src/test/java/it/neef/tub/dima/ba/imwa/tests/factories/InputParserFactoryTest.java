package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.parser.InputParserFactory;
import it.neef.tub.dima.ba.imwa.impl.parser.InputParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class InputParserFactoryTest {
    @Test
    public void newInputParser() throws Exception {
        InputParserFactory pf = new InputParserFactory();
        assertEquals(InputParser.class, pf.newInputParser().getClass());
    }

}
package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.factories.parser.DumpParserFactory;
import it.neef.tub.dima.ba.imwa.impl.parser.DumpParser;
import it.neef.tub.dima.ba.imwa.impl.parser.SkipDumpParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class DumpParserFactoryTest {
    @Test
    public void newDumpParser() throws Exception {
        DumpParserFactory dpf = new DumpParserFactory();
        assertEquals(SkipDumpParser.class, dpf.newDumpParser().getClass());
    }

}
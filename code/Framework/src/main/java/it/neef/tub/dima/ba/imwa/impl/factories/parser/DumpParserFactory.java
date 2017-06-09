package it.neef.tub.dima.ba.imwa.impl.factories.parser;

import it.neef.tub.dima.ba.imwa.impl.parser.SkipDumpParser;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IDumpParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IDumpParser;

/**
 * Factory for DumpParser objects. Here, the SkipDumpParser.
 *
 * @see IDumpParser
 * Created by gehaxelt on 09.10.16.
 */
public class DumpParserFactory implements IDumpParserFactory {

    @Override
    public IDumpParser newDumpParser() {
        return new SkipDumpParser();
    }
}

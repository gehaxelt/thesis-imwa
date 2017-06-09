package it.neef.tub.dima.ba.imwa.interfaces.factories.parser;

import it.neef.tub.dima.ba.imwa.interfaces.parser.IDumpParser;

import java.io.Serializable;

/**
 * Interface for a factory of DumpParsers
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDumpParserFactory<T extends IDumpParser> extends Serializable {

    /**
     * Should instantiate a new IDumpParser object.
     *
     * @return the new DumpParser object of type T
     * @see IDumpParser
     */
    T newDumpParser();
}

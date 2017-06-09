package it.neef.tub.dima.ba.imwa.interfaces.factories.parser;


import it.neef.tub.dima.ba.imwa.interfaces.parser.AInputParser;

import java.io.Serializable;

/**
 * Interface for a factory of InputParsers
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IInputParserFactory<T extends AInputParser> extends Serializable {

    /**
     * Should instantiate a new AInputParser object.
     *
     * @return the new AInputParser object of type T
     * @see AInputParser
     */
    T newInputParser();
}

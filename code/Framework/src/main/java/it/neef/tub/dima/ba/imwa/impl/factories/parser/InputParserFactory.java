package it.neef.tub.dima.ba.imwa.impl.factories.parser;

import it.neef.tub.dima.ba.imwa.impl.parser.InputParser;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IInputParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.parser.AInputParser;

/**
 * Factory for InputParser objects.
 *
 * @see AInputParser
 * Created by gehaxelt on 09.10.16.
 */
public class InputParserFactory implements IInputParserFactory {
    @Override
    public AInputParser newInputParser() {
        return new InputParser();
    }
}

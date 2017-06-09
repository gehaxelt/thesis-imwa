package it.neef.tub.dima.ba.imwa.impl.factories.output;

import it.neef.tub.dima.ba.imwa.impl.output.ConsoleOutput;
import it.neef.tub.dima.ba.imwa.interfaces.factories.output.IOutputFactory;
import it.neef.tub.dima.ba.imwa.interfaces.output.IOutput;

/**
 * Factory for ConsoleOutput objects.
 *
 * @see IOutput
 * Created by gehaxelt on 06.12.16.
 */
public class OutputFactory implements IOutputFactory<IOutput> {
    @Override
    public IOutput newOutput() {
        return new ConsoleOutput();
    }
}

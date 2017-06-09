package it.neef.tub.dima.ba.imwa.interfaces.factories.output;

import it.neef.tub.dima.ba.imwa.interfaces.output.IOutput;

import java.io.Serializable;

/**
 * Interface for a factory of Output classes.
 * <p>
 * Created by gehaxelt on 06.12.16.
 */
public interface IOutputFactory<O extends IOutput> extends Serializable {
    /**
     * Should instantiate an Output object.
     *
     * @return new Output object of type O
     * @see IOutput
     */
    O newOutput();
}

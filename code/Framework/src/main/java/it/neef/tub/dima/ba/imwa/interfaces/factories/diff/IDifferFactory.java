package it.neef.tub.dima.ba.imwa.interfaces.factories.diff;

import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

import java.io.Serializable;

/**
 * Interface for a factory of Differs.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDifferFactory<D extends IDiffer> extends Serializable {

    /**
     * Should instantiate a Differ of type D.
     *
     * @return new instance of a Differ.
     * @see IDiffer
     */
    D newDiffer();
}

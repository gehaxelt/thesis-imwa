package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;

import java.io.Serializable;

/**
 * Interface for a factory of Differences.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDifferenceFactory<T extends IDifference> extends Serializable {

    /**
     * Should instantiate a new Difference object.
     *
     * @return new Difference instance
     * @see IDifference
     */
    T newDifference();
}

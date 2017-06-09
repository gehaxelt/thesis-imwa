package it.neef.tub.dima.ba.imwa.interfaces.data;

import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

/**
 * Interface for revision differences.
 * A difference is calculated by the {@link IDiffer} between
 * two consecutive revisions.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDifference<T> extends IDataType {

    /**
     * Getter for a difference object.
     *
     * @return the difference of type T.
     * @see Object
     */
    T getDifference();

    /**
     * Setter for the difference object.
     *
     * @param difference the new difference.
     */
    void setDifference(T difference);
}

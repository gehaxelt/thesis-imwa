package it.neef.tub.dima.ba.imwa.interfaces.diff;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;

/**
 * Interface for a Differ class which handles the calculation
 * of differences between two revisions.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDiffer<D extends IDifference, R extends IRevision> {

    /**
     * Should calculate the differences between two revisions.
     *
     * @param current the current revision from which to start (usually r_i)
     * @param next    the next revision to diff against (usually r_(i+1)) or null if it is not needed.
     * @return the differences between both revisions (current -> next) in a wrapper object of type D.
     * @see IDifference
     * @see IRevision
     */
    D calculateDiff(R current, R next);
}

package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;

import java.io.Serializable;

/**
 * Interface for a factory of Revisions.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IRevisionFactory<T extends IRevision> extends Serializable {
    /**
     * Should instantiate a new Revision object.
     *
     * @return new Revision instance
     * @see IRevision
     */
    T newRevision();
}

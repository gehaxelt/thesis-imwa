package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;

import java.io.Serializable;

/**
 * Interface for a factory of Contributors.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IContributorFactory<T extends IContributor> extends Serializable {

    /**
     * Should instantiate a new Contributor object.
     *
     * @return new Contributor instance
     * @see IContributor
     */
    T newContributor();
}

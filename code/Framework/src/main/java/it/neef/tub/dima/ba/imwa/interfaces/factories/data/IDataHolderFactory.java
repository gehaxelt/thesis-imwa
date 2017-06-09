package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDataHolder;

import java.io.Serializable;

/**
 * Interface for a factory of DataHolder.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDataHolderFactory<T extends IDataHolder> extends Serializable {

    /**
     * Should instantiate a new DataHolder object.
     *
     * @return new DataHolder instance
     * @see IDataHolder
     */
    T newDataHolder();
}

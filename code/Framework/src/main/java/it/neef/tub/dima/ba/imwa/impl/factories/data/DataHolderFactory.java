package it.neef.tub.dima.ba.imwa.impl.factories.data;


import it.neef.tub.dima.ba.imwa.impl.data.DataHolder;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDataHolder;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IDataHolderFactory;

/**
 * Factory for DataHolder objects.
 *
 * @see IDataHolder
 * Created by gehaxelt on 08.10.16.
 */
public class DataHolderFactory implements IDataHolderFactory<IDataHolder> {
    @Override
    public IDataHolder newDataHolder() {
        return new DataHolder();
    }
}

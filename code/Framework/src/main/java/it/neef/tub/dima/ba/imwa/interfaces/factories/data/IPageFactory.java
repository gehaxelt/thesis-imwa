package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;

import java.io.Serializable;

/**
 * Interface for a factory of Pages.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IPageFactory<T extends IPage<IRevision, IPageview>> extends Serializable {
    /**
     * Should instantiate a new Page object.
     *
     * @return new Page instance
     * @see IPage
     */
    T newPage();
}

package it.neef.tub.dima.ba.imwa.interfaces.factories.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;

import java.io.Serializable;

/**
 * Interface for a factory of Pageviews.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public interface IPageviewFactory<V extends IPageview> extends Serializable {
    /**
     * Should instantiate a new Pageview object.
     *
     * @return new Pageview instance
     * @see IPageview
     */
    V newPageview();
}

package it.neef.tub.dima.ba.imwa.impl.factories.data;

import it.neef.tub.dima.ba.imwa.impl.data.Pageview;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IPageviewFactory;

/**
 * Factory for Pageview objects.
 *
 * @see IPageview
 * Created by gehaxelt on 09.10.16.
 */
public class PageviewFactory implements IPageviewFactory<IPageview> {
    @Override
    public IPageview newPageview() {
        return new Pageview();
    }
}

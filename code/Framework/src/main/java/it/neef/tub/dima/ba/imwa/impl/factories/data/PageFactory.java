package it.neef.tub.dima.ba.imwa.impl.factories.data;

import it.neef.tub.dima.ba.imwa.impl.data.Page;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IPageFactory;

/**
 * Factory for Page objects.
 *
 * @see IPage
 * Created by gehaxelt on 08.10.16.
 */
public class PageFactory implements IPageFactory<IPage<IRevision, IPageview>> {
    @Override
    public IPage<IRevision, IPageview> newPage() {
        return new Page();
    }
}

package it.neef.tub.dima.ba.imwa.impl.factories.data;

import it.neef.tub.dima.ba.imwa.impl.data.Revision;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IRevisionFactory;

/**
 * Factory for Revision objects.
 *
 * @see IRevision
 * Created by gehaxelt on 08.10.16.
 */
public class RevisionFactory implements IRevisionFactory<IRevision> {
    @Override
    public IRevision newRevision() {
        return new Revision();
    }
}

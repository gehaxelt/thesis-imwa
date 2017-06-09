package it.neef.tub.dima.ba.imwa.impl.factories.data;

import it.neef.tub.dima.ba.imwa.impl.data.Contributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IContributorFactory;

/**
 * Factory for Contributor objects.
 *
 * @see IContributor
 * Created by gehaxelt on 08.10.16.
 */
public class ContributorFactory implements IContributorFactory<IContributor> {
    @Override
    public IContributor newContributor() {
        return new Contributor();
    }
}

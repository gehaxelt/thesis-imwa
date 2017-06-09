package it.neef.tub.dima.ba.imwa.impl.factories.data;

import it.neef.tub.dima.ba.imwa.impl.data.DoubleDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IDifferenceFactory;

/**
 * Factory for Difference objects.
 *
 * @see IDifference
 * Created by gehaxelt on 08.10.16.
 */
public class DifferenceFactory implements IDifferenceFactory<IDifference> {
    @Override
    public IDifference newDifference() {
        return new DoubleDifference();
    }
}

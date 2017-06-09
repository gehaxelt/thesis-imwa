package it.neef.tub.dima.ba.imwa.impl.factories.diff;

import it.neef.tub.dima.ba.imwa.impl.diff.LengthDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.factories.diff.IDifferFactory;

/**
 * Factory for LengthDiffer objects.
 *
 * @see LengthDiffer
 * Created by gehaxelt on 09.10.16.
 */
public class LengthDifferFactory implements IDifferFactory<IDiffer> {
    @Override
    public IDiffer newDiffer() {
        return new LengthDiffer();
    }
}

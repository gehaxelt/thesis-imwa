package it.neef.tub.dima.ba.imwa.impl.diff;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

/**
 * Class for calculating length differences of the text of two revisions.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class LengthDiffer implements IDiffer<IDifference<Integer>, IRevision> {
    @Override
    public IDifference<Integer> calculateDiff(IRevision parent, IRevision current) {
        // If there is no parent revision, the length is 0.
        int beforeLength = 0;
        if (parent != null) {
            beforeLength = parent.getText().length();
        }
        // The current revision's text length.
        int afterLength = current.getText().length();

        // Return new IDifference with the absolute of the subtraction of both lengths.
        IDifference<Integer> ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();
        ld.setDifference(new Integer(Math.abs(beforeLength - afterLength)));
        return ld;
    }
}

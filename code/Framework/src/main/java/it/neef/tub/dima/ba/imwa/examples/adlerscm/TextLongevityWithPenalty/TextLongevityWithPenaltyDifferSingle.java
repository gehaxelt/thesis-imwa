package it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevityWithPenalty;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.AdlerContributionMeasuresUtils;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity.EditLongevityDiffer;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditOnly.EditOnlyDiffer;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextOnly.TextOnlyDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

import java.util.ArrayList;

/**
 * Differ for Adler's TextLongevity contribution measure, by solving the equation for alpha_text and then multiplying
 * it with txt(r)
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 15.02.17.
 */
public class TextLongevityWithPenaltyDifferSingle implements IDiffer<IDifference<Double>, IRevision> {

    /**
     * The current revision for which we want to calculate the value.
     */
    private IRevision r_i;

    private double editLongevity() {
        EditLongevityDiffer editLongevityDiffer = new EditLongevityDiffer();
        return Math.min(0, editLongevityDiffer.calculateDiff(this.r_i, null).getDifference());
    }

    private double editOnly() {
        EditOnlyDiffer editOnlyDiffer = new EditOnlyDiffer();
        return editOnlyDiffer.calculateDiff(this.r_i.getParentRevision(), this.r_i).getDifference();
    }

    /**
     * Implements the alpha_text(r) measure
     *
     * @param current the current revision from which to start (usually r_i)
     * @param next    the next revision to diff against (usually r_(i+1)) or null if it is not needed.
     * @return the solution for the equation, which is the wanted alpha_text(r) value.
     */
    @Override
    public IDifference<Double> calculateDiff(IRevision current, IRevision next) {
        this.r_i = current;
        IDifference ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();
        ld.setDifference(this.editLongevity() * this.editOnly());
        return ld;
    }
}

package it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.AdlerContributionMeasuresUtils;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditOnly.EditOnlyDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

import java.util.ArrayList;

/**
 * Differ for Adler's EditLongevity contribution measure, by implementing the alpha'_edit(r).
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 17.01.17.
 */
public class EditLongevityDiffer implements IDiffer<IDifference<Double>, IRevision> {

    /**
     * In their paper, Adler et al. suggest 10 jugdes.
     */
    private final static int MAX_JUDGES = 10;

    /**
     * The current revision for which we want to calculate the value.
     */
    private IRevision r_i;

    /**
     * THe list of the following MAX_JUDGES revisions.
     */
    private ArrayList<IRevision> jMap = null;

    /**
     * Calculate the alpha_edit(v_i, v_j) quality measure.
     *
     * @param r_i the start revision.
     * @param r_j the end revision.
     * @return the alpha_edit(v_i, v_j) value.
     */
    private double alphaEdit(IRevision r_i, IRevision r_j) {
        // First, we have to calculate three edit distances.
        EditOnlyDiffer editOnlyDiffer = new EditOnlyDiffer();
        IDifference<Double> d1 = editOnlyDiffer.calculateDiff(r_i.getParentRevision(), r_j);
        IDifference<Double> d2 = editOnlyDiffer.calculateDiff(r_i, r_j);
        IDifference<Double> d3 = editOnlyDiffer.calculateDiff(r_i.getParentRevision(), r_i);

        if (d3.getDifference() == 0.0) {
            return 0.0;
        }
        // Then, we put that values into the formula.
        double alphaEdit = (d1.getDifference() - d2.getDifference()) / d3.getDifference();

        // The values should be capped at [-1, +1]
        if (alphaEdit < -1) {
            alphaEdit = -1;
        }
        if (alphaEdit > +1) {
            alphaEdit = +1;
        }
        return alphaEdit;
    }

    /**
     * Calculate the sum part of the formula: sum_{r_j \in jMap} alpha_edit(r_i, r_j)
     *
     * @return the sum part of the alpha'_edit(r) formula.
     */
    private double sumAlphaEdit() {
        double sum = 0.0;
        for (IRevision r_j : this.jMap) {
            sum += this.alphaEdit(this.r_i, r_j);
        }
        return sum;
    }

    /**
     * Calculates the alpha'_edit(r) value.
     *
     * @param current the current revision from which to start (usually r_i)
     * @param next    the next revision to diff against (usually r_(i+1)) or null if it is not needed.
     * @return the resulting value for alpha'_edit(r)
     */
    @Override
    public IDifference<Double> calculateDiff(IRevision current, IRevision next) {
        this.r_i = current;
        IDifference ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();
        this.jMap = AdlerContributionMeasuresUtils.getJMap(this.r_i, MAX_JUDGES);
        if (this.jMap.size() == 0) {
            //Only work with J != \emptyset
            ld.setDifference(0.0);
        } else {
            Double difference = (1.0 / this.jMap.size());
            difference *= this.sumAlphaEdit();
            ld.setDifference(difference);
        }
        return ld;
    }
}

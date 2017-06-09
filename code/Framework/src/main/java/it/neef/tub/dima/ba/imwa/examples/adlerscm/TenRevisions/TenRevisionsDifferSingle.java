package it.neef.tub.dima.ba.imwa.examples.adlerscm.TenRevisions;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.AdlerContributionMeasuresUtils;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextOnly.TextOnlyDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;

import java.util.ArrayList;

/**
 * Differ for Adler's TenRevisvions contribution measure, by implementing the beta_text(r) measure and the TextOnlyDiffer.
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 06.02.17.
 */
public class TenRevisionsDifferSingle implements IDiffer<IDifference<Double>, IRevision> {

    /**
     * In their paper, Adler et al. suggest 10 jugdes.
     */
    private final static int MAX_JUDGES = 10;

    /**
     * The current revision for which we want to calculate the value.
     */
    private IRevision r_i;

    /**
     * The TextOnlyDiffer used for solving the equation of alpha_text.
     */
    private TextOnlyDiffer textOnlyDiffer = null;

    /**
     * The list of the following MAX_JUDGES revisions.
     */
    private ArrayList<IRevision> jMap = null;

    /**
     * Calculate the beta_text(r) value as described in the paper.
     *
     * @return the beta_text(r) value for the revision r_i.
     */
    private Double sumBetaEdit() {
        Double diff = this.textOnlyDiffer.calculateDiff(this.r_i, this.r_i).getDifference();
        if(diff == 0.0) {
            // Return 0.0, because we don't want NaN results and multiplying with 0.0 doesn't change the result.
            return 0.0;
        }
        Double betaScore = (1.0 / diff );
        Double sum = 0.0;
        for (IRevision r_j : this.jMap) {
            sum += this.textOnlyDiffer.calculateDiff(this.r_i, r_j).getDifference();
        }
        betaScore *= sum;
        return betaScore;
    }

    private double textOnly() {
        return this.textOnlyDiffer.calculateDiff(this.r_i, this.r_i).getDifference();
    }

    /**
     * Implements the beta_text(r) measure.
     *
     * @param current the current revision from which to start (usually r_i)
     * @param next    the next revision to diff against (usually r_(i+1)) or null if it is not needed.
     * @return the resulting value for beta_text(r)
     */
    @Override
    public IDifference<Double> calculateDiff(IRevision current, IRevision next) {
        this.r_i = current;
        IDifference ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();
        this.jMap = AdlerContributionMeasuresUtils.getJMap(this.r_i, MAX_JUDGES);
        this.textOnlyDiffer = new TextOnlyDiffer();

        ld.setDifference(this.sumBetaEdit() * this.textOnly());

        return ld;
    }
}

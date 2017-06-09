package it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevity;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.AdlerContributionMeasuresUtils;
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
public class TextLongevityDifferSingle implements IDiffer<IDifference<Double>, IRevision> {

    /**
     * In their paper, Adler et al. suggest 10 jugdes.
     */
    private final static int MAX_JUDGES = 10;

    /**
     * Adler et. al say 5 iterations for the Newton method are enough.
     */
    private final static int MAX_ITERATIONS = 5;

    /**
     * The current revision for which we want to calculate the value.
     */
    private IRevision r_i;

    /**
     * The list of the following MAX_JUDGES revisions.
     */
    private ArrayList<IRevision> jMap = null;

    /**
     * The TextOnlyDiffer used for solving the equation of alpha_text.
     */
    private TextOnlyDiffer textOnlyDiffer = null;

    /**
     * The equation's txt(v_i, v_i) value calculated with the TextOnlyDiffer.
     */
    private Double txt_ii = 0.0;

    /**
     * The equation's sum of the differences of the current revision with all revisions from the jMap. sum_{r_j \in jMap} txt(r_i, r_j)
     */
    private Double txt_ij_sum = 0.0;

    /**
     * The equation transformed into a function for calculating the its zeroes.
     *
     * @param x the alpha_text value
     * @return the result of f(x)
     */
    private Double f(Double x) {
        //from paper - This one seems more reliable and more logical than their implemented version below.
        return -(1.0 - Math.pow(x, MAX_JUDGES + 1.0)) * this.txt_ii + (1.0 - x) * this.txt_ij_sum;

        //from their code - https://github.com/collaborativetrust/WikiTrust/blob/master/analysis/wikidata.ml#L314, last accessed 22.03.2017
        //return (1.0 - x) * this.txt_ij_sum - x*this.txt_ii*(1.0 - Math.pow(x, MAX_JUDGES));
    }

    /**
     * The derivation of the function "f".
     *
     * @param x the alpha_text value.
     * @return the result of f'(x)
     */
    private Double f_(Double x) {
        //from paper - The derivation of the function f from above.
        return this.txt_ii * Math.pow(x, MAX_JUDGES) * (MAX_JUDGES + 1.0) - this.txt_ij_sum;

        //from their code - https://github.com/collaborativetrust/WikiTrust/blob/master/analysis/wikidata.ml#L317, last accessed 22.03.2017
        //return -this.txt_ij_sum -this.txt_ii*(1.0 - Math.pow(x, MAX_JUDGES)) + x*this.txt_ii*MAX_JUDGES*Math.pow(x, MAX_JUDGES -1);
    }

    /**
     * Initializes the Newton method by calculating the needed values txt_ii and txt_ij.
     *
     * @see #txt_ii
     * @see #txt_ij_sum
     */
    private void initNewton() {
        this.txt_ii = this.textOnlyDiffer.calculateDiff(this.r_i, this.r_i).getDifference();
        this.txt_ij_sum += this.txt_ii;
        for (IRevision r_j : this.jMap) {
            this.txt_ij_sum += this.textOnlyDiffer.calculateDiff(this.r_i, r_j).getDifference();
        }
    }

    /**
     * Uses the Newton method to solve the TextLongevity equation for the needed alpha_text variable.
     *
     * @return the closest approximation of alpha_text after MAX_ITERATIONS.
     */
    private Double solveWithNewton() {
        this.initNewton();
        if (this.txt_ii == 0) {
            return 1.0;
        }
        if (this.txt_ij_sum == 0) {
            return 0.0;
        }
        Double x = 0.0;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            x = x - (this.f(x) / this.f_(x));
        }
        //System.out.println("TextLongevity for " + this.r_i + ">> " + String.valueOf(this.txt_ii) + " / " + String.valueOf(this.txt_ij_sum) + " => " + String.valueOf(x));

        return x;
    }

    private double textOnly() {
        return this.textOnlyDiffer.calculateDiff(this.r_i, this.r_i).getDifference();
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
        this.jMap = AdlerContributionMeasuresUtils.getJMap(this.r_i, MAX_JUDGES);
        this.textOnlyDiffer = new TextOnlyDiffer();
        IDifference ld = Framework.getInstance().getConfiguration().getDifferenceFactory().newDifference();
        ld.setDifference(this.solveWithNewton() * this.textOnly());
        return ld;
    }
}

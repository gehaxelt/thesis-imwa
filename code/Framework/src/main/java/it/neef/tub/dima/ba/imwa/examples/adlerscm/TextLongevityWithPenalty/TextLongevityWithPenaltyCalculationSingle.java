package it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevityWithPenalty;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.AdlerContributionMeasuresUtils;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity.EditLongevityDiffer;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity.EditLongevityDifferSingle;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevity.TextLongevityCalculation;
import it.neef.tub.dima.ba.imwa.impl.calc.ArgumentsBundle;
import it.neef.tub.dima.ba.imwa.interfaces.calc.ACalculation;
import it.neef.tub.dima.ba.imwa.interfaces.calc.IRelevanceAggregator;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;

/**
 * Calculation which implements Adler's TextLongevity contribution measure.
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 15.02.17.
 */
public class TextLongevityWithPenaltyCalculationSingle extends ACalculation<IContributor, Double, IRevision, ArgumentsBundle> {

    /**
     * The final rated contributor RelevanceScore DataSet.
     */
    private DataSet<IRelevanceScore<IContributor, Double>> contributorRelevanceScoreDataSet;

    /**
     * The DataSet with the parsed revisions.
     */
    private DataSet<IRevision> iRevisionDataSet;

    /**
     * The contributor RelevanceScore DataSet after the TextLongevityCalculation.
     */
    private DataSet<IRelevanceScore> textLongevityRelevanceScoreDataSet;

    @Override
    public ArgumentsBundle getArguments() {
        return null;
    }

    @Override
    public void setArguments(ArgumentsBundle arguments) {
    }

    @Override
    public DataSet<? extends IRelevanceScore<IContributor, Double>> getResult() {
        return this.contributorRelevanceScoreDataSet;
    }

    @Override
    public DataSet<? extends IRelevanceScore<IRevision, Double>> getBaseResult() {
        return this.iRevisionDataSet.map(new MapFunction<IRevision, IRelevanceScore<IRevision, Double>>() {
            @Override
            public IRelevanceScore<IRevision, Double> map(IRevision iRevision) throws Exception {
                IRelevanceScore<IRevision, Double> score = Framework.getInstance().getConfiguration().getRelevanceScoreFactory().newRevisionRelevanceScore();
                score.setObject(iRevision);
                score.setRelevanceScore((Double) iRevision.getDifference().getDifference());
                return score;
            }
        });
    }
    /**
     * Initialize this calculation by computing the TextLongevity and EditOnly contribution measure. This is txt(r) and d(r) for all revisions.
     */
    @Override
    public void init() {
        try {
            ACalculation textLongevity = new TextLongevityCalculation();
            Framework.getInstance().runCalculation(textLongevity);
            this.textLongevityRelevanceScoreDataSet = textLongevity.getResult();

        } catch (Exception e) {
            this.textLongevityRelevanceScoreDataSet= null;
        }
    }

    @Override
    public void preProcess() {
        // Get the Revision DataSet and calculate the EditLongevity contribution measure on all of its items.
        this.iRevisionDataSet = ((DataSet<IRevision>) Framework.getInstance().getConfiguration().getDataHolder().getRevisions());
        this.iRevisionDataSet = this.iRevisionDataSet.map(new MapFunction<IRevision, IRevision>() {
            @Override
            public IRevision map(IRevision iRevision) throws Exception {
                IDiffer differ = new TextLongevityWithPenaltyDifferSingle();
                // We calculate the alpha'_edit(r) * d(r)
                IDifference diff = differ.calculateDiff(iRevision, null); //we only pass r_i

                iRevision.setDifference(diff);
                return iRevision;
            }
        });

    }

    @Override
    public void process() {
        // Get the final contribution RelevanceScore DataSet from the Revisions.
        this.contributorRelevanceScoreDataSet = AdlerContributionMeasuresUtils.revisionDataSetToContributorScore(this.iRevisionDataSet);
    }

    @Override
    public void postProcess() {
        IRelevanceAggregator ag = Framework.getInstance().getConfiguration().getRelevanceAggregator();
        this.contributorRelevanceScoreDataSet = ag.add(this.textLongevityRelevanceScoreDataSet, this.contributorRelevanceScoreDataSet);
    }
}

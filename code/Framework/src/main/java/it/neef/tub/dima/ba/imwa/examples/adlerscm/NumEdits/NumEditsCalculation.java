package it.neef.tub.dima.ba.imwa.examples.adlerscm.NumEdits;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.impl.calc.ArgumentsBundle;
import it.neef.tub.dima.ba.imwa.impl.data.ContributorRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.calc.ACalculation;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;

/**
 * Calculation which implements Adler's NumEdits contribution measure.
 *
 * @see <a href="https://users.soe.ucsc.edu/~luca/papers/08/wikisym08-users.pdf">the paper</a>
 * Created by gehaxelt on 03.01.17.
 */
public class NumEditsCalculation extends ACalculation<IContributor, Double, IContributor, ArgumentsBundle> {

    /**
     * The final rated contributor RelevanceScore DataSet.
     */
    private DataSet<IRelevanceScore<IContributor, Double>> contributorRelevanceScoreDataSet;

    /**
     * The DataSet with the parsed revisions.
     */
    private DataSet<IContributor> iContributorDataSet;

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
    public DataSet<? extends IRelevanceScore<IContributor, Double>> getBaseResult() {

        return this.iContributorDataSet.map(new MapFunction<IContributor, IRelevanceScore<IContributor, Double>>() {
            @Override
            public IRelevanceScore<IContributor, Double> map(IContributor iContributor) throws Exception {

                            IRelevanceScore<IContributor, Double> score = Framework.getInstance().getConfiguration().getRelevanceScoreFactory().newContributorRelevanceScore();
                            score.setObject(iContributor);
                            score.setRelevanceScore((Double) 1.0);
                            return score;
                }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void preProcess() {
        // Get the Revision DataSet and set the relevance to 1.0 for every revision.  This will sum up to the number of edits in the process() step.
        this.iContributorDataSet = ((DataSet<IContributor>) Framework.getInstance().getConfiguration().getDataHolder().getContributors());
    }

    @Override
    public void process() {
        this.contributorRelevanceScoreDataSet = this.iContributorDataSet.map(new MapFunction<IContributor, IRelevanceScore<IContributor, Double>>() {
            @Override
            public IRelevanceScore<IContributor, Double> map(IContributor iContributor) throws Exception {
                IRelevanceScore<IContributor, Double> score = Framework.getInstance().getConfiguration().getRelevanceScoreFactory().newContributorRelevanceScore();
                score.setObject(iContributor);
                score.setRelevanceScore((Double) 1.0);
                return score;
            }
        }).filter(new FilterFunction<IRelevanceScore<IContributor, Double>>() {
            @Override
            public boolean filter(IRelevanceScore<IContributor, Double> iContributorDoubleIRelevanceScore) throws Exception {
                // We don't want rating for non-existent Contributors.
                return iContributorDoubleIRelevanceScore.getObject().getUsername() != null && !iContributorDoubleIRelevanceScore.getObject().getUsername().equals("");
            }
        }).groupBy(new KeySelector<IRelevanceScore<IContributor, Double>, Long>() {
            @Override
            public Long getKey(IRelevanceScore<IContributor, Double> iContributorDoubleIRelevanceScore) throws Exception {
                // We want to group by the contributor's identifier in case we have multiple revisions by the same contributor.
                return iContributorDoubleIRelevanceScore.getIdentifier();
            }
        }).reduce(new ReduceFunction<IRelevanceScore<IContributor, Double>>() {
            @Override
            public ContributorRelevanceScore reduce(IRelevanceScore<IContributor, Double> iContributorDoubleIRelevanceScore, IRelevanceScore<IContributor, Double> t1) throws Exception {
                // Sum the values if there are multiple ContributorRelevanceScore and produce only one, because we want one final value.
                ContributorRelevanceScore score = (ContributorRelevanceScore) Framework.getInstance().getConfiguration().getRelevanceScoreFactory().newContributorRelevanceScore();
                score.setObject(iContributorDoubleIRelevanceScore.getObject());
                score.setRelevanceScore(iContributorDoubleIRelevanceScore.getRelevanceScore() + t1.getRelevanceScore());
                return score;
            }
        });

    }

    @Override
    public void postProcess() {

    }
}

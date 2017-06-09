package it.neef.tub.dima.ba.imwa.examples.adlerscm;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.impl.data.ContributorRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.functions.KeySelector;

import java.util.ArrayList;

/**
 * Created by gehaxelt on 22.03.17.
 */
public class AdlerContributionMeasuresUtils {

    /**
     * Compiles the list of the next MAX_JUDGES judges for the revision r_i.
     *
     * @param r_i        the Revision for which judges need to be found.
     * @param MAX_JUDGES the amount of judges to call.
     * @return a list of judges for the revision r_i.
     */
    public static ArrayList<IRevision> getJMap(IRevision r_i, int MAX_JUDGES) {
        ArrayList<IRevision> J = new ArrayList<>();
        IRevision child = r_i.getChildRevision();
        for (int i = 0; i < MAX_JUDGES && child != null; i++) {
            J.add(child);
            child = child.getChildRevision();
        }
        return J;
    }

    /**
     * Transforms a Revision DataSet with already calculated differences into its respective contributor RelevanceScore DataSet.
     *
     * @param revisions the diff'ed Revision DataSet
     * @return the DataSet with the contributors RelevanceScores.
     */
    public static DataSet<IRelevanceScore<IContributor, Double>> revisionDataSetToContributorScore(DataSet<IRevision> revisions) {
        // Don't work with null DataSets.
        if (revisions == null) {
            return null;
        }

        return revisions.map(new MapFunction<IRevision, IRelevanceScore<IContributor, Double>>() {
            @Override
            public IRelevanceScore<IContributor, Double> map(IRevision iRevision) throws Exception {
                // Convert the diff'ed Revision DataSet into a contributor RelevanceScore DataSet.
                IRelevanceScore<IContributor, Double> score = Framework.getInstance().getConfiguration().getRelevanceScoreFactory().newContributorRelevanceScore();
                score.setObject(iRevision.getContributor());
                score.setRelevanceScore((Double) iRevision.getDifference().getDifference());
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


    /**
     * Transforms a RevisionScore DataSet into its respective contributor RelevanceScore DataSet.
     *
     * @param revisions the diff'ed Revision DataSet
     * @return the DataSet with the contributors RelevanceScores.
     */
    public static DataSet<IRelevanceScore<IContributor, Double>> revisionScoreDataSetToContributorScore(DataSet<IRelevanceScore<IRevision,Double>> revisions) {
        // Don't work with null DataSets.
        if (revisions == null) {
            return null;
        }

        return revisions.map(new MapFunction<IRelevanceScore<IRevision,Double>, IRelevanceScore<IContributor, Double>>() {
            @Override
            public IRelevanceScore<IContributor, Double> map(IRelevanceScore<IRevision,Double> iRevision) throws Exception {
                // Convert the diff'ed Revision DataSet into a contributor RelevanceScore DataSet.
                IRelevanceScore<IContributor, Double> score = Framework.getInstance().getConfiguration().getRelevanceScoreFactory().newContributorRelevanceScore();
                score.setObject(iRevision.getObject().getContributor());
                score.setRelevanceScore((Double) iRevision.getRelevanceScore());
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
}

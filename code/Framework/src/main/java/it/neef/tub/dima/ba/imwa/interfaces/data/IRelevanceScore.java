package it.neef.tub.dima.ba.imwa.interfaces.data;


import it.neef.tub.dima.ba.imwa.interfaces.calc.IRelevanceAggregator;
import it.neef.tub.dima.ba.imwa.interfaces.output.IOutput;

/**
 * Interface for a RelevanceScore class.
 * A RelevanceScore object is a wrapper around an IDataType object and
 * the associated score. It should also provide an unique identifier, so
 * that group operations can be applied to RelevanceScore-DataSets.
 * The scores can later be aggregated using the {@link IRelevanceAggregator}
 * or outputted using the {@link IOutput } classes.
 * <p>
 * Created by gehaxelt on 23.10.16.
 */
public interface IRelevanceScore<O extends IDataType, S extends Double> extends IDataType {

    /**
     * Getter for the actual relevance score of associated object.
     *
     * @return revelance score
     * @see Double
     */
    S getRelevanceScore();

    /**
     * Setter for the relevance score.
     *
     * @param score the new score.
     * @see #getRelevanceScore
     */
    void setRelevanceScore(S score);

    /**
     * Getter for an IDataType object that will have a score.
     *
     * @return the rated object.
     * @see IDataType
     */
    O getObject();

    /**
     * Setter for the rated object.
     *
     * @param object the new object.
     * @see IDataType
     */
    void setObject(O object);

    /**
     * Getter for an unique identifier of the rated object.
     * Returning this.object.getIdentifier() should be enough.
     *
     * @return the unique identifier which identifies the rated object.
     */
    long getIdentifier();
}

package it.neef.tub.dima.ba.imwa.interfaces.calc;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDataType;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;

/**
 * Interface for a Calculation function.
 * This class should be used to write the formula which will then applied on the different
 * DataSets. The result should be a {@link IRelevanceScore} DataSet consisting of the calculated
 * scores for the specific objects.
 * <p>
 * Created by gehaxelt on 23.10.16.
 */
public interface ICalculation<T extends IDataType, S extends Double, B extends IDataType, A extends IArgumentsBundle> extends Serializable {


    /**
     * Getter for the calculation's additional arguments.
     *
     * @return the calculation's arguments.
     */
    A getArguments();

    /**
     * Setter for the calculation's additional arguments.
     *
     * @param arguments the arguments to pass to the calculation.
     * @see IArgumentsBundle
     */
    void setArguments(A arguments);

    /**
     * Getter for the calculation's result DataSet.
     * This DataSet should be the final result of the computation and
     * contain the relevance scores for each object.
     *
     * @return the resulting relevance score DataSet.
     * @see IRelevanceScore
     */
    DataSet<? extends IRelevanceScore<T, S>> getResult();

    /***
     * Getter for the calculation's base DataSet and from which the result is calculated.
     * This is mainly useful for the RelevanceAggregator.
     *
     * @return the DataSet used for calculating the relevance score DataSet.
     */
    DataSet<? extends IRelevanceScore<B, S>> getBaseResult();

    /**
     * This processing step is called first. It should be used to
     * initialize the calculation.
     */
    void init();

    /**
     * This processing step is the second one. It should be used to
     * preprocess the DataSets for the calculation.
     */
    void preProcess();

    /**
     * This is the third processing step. It should be used to do the main
     * calculation work.
     */
    void process();

    /**
     * This is the last processing step. It should be used to postprocess the resulting
     * relevance score DataSet.
     */
    void postProcess();

    /**
     * This method is called to start the processing of the calculation. It should execute all
     * four processing steps.
     */
    void run();
}

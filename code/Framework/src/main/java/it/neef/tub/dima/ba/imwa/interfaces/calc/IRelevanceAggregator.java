package it.neef.tub.dima.ba.imwa.interfaces.calc;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDataType;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRelevanceScore;
import it.neef.tub.dima.ba.imwa.interfaces.data.IIdentifiable;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;

/**
 * Interface for a class which facilitates the aggregation of two {@link IRelevanceScore} DataSets
 * in various ways. It should support addition, subtraction, multiplication, division where matching
 * elements (by their {@link IIdentifiable} identifier) are used as operands.
 * Additionally the calculation of the sum, min, max aggregations is possible.
 * Scalar operations like addition, subtraction, multiplication and division can also be achieved.
 * <p>
 * Created by gehaxelt on 26.10.16.
 */
public interface IRelevanceAggregator<D extends IDataType, S extends Double, Q extends IRelevanceScore<D, S>> extends Serializable {

    /**
     * Performs an addition between matching elements: left + right
     *
     * @param left  the left operand.
     * @param right the right operand.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     */
    DataSet<Q> add(DataSet<Q> left, DataSet<Q> right);

    /**
     * Performs a subtraction between matching elements: left - right
     *
     * @param left  the left operand.
     * @param right the right operand.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     */
    DataSet<Q> sub(DataSet<Q> left, DataSet<Q> right);

    /**
     * Performs a multiplication between matching elements: left * right
     *
     * @param left  the left operand.
     * @param right the right operand.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     */
    DataSet<Q> mul(DataSet<Q> left, DataSet<Q> right);

    /**
     * Performs a division between matching elements: left / right
     *
     * @param left  the left operand.
     * @param right the right operand.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     */
    DataSet<Q> div(DataSet<Q> left, DataSet<Q> right);

    /**
     * Performs an addition of each object with a given scalar: data + value
     *
     * @param data  the left operand.
     * @param value the scalar.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     * @see Double
     */
    DataSet<Q> addValue(DataSet<Q> data, S value);

    /**
     * Performs a subtraction of each object with a given scalar: data - value
     *
     * @param data  the left operand.
     * @param value the scalar.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     * @see Double
     */
    DataSet<Q> subValue(DataSet<Q> data, S value);

    /**
     * Performs a multiplication of each object with a given scalar: data * value
     *
     * @param data  the left operand.
     * @param value the scalar.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     * @see Double
     */
    DataSet<Q> mulValue(DataSet<Q> data, S value);

    /**
     * Performs a division of each object with a given scalar: data / value
     *
     * @param data  the left operand.
     * @param value the scalar.
     * @return the new DataSet with the results.
     * @see IRelevanceScore
     * @see Double
     */
    DataSet<Q> divValue(DataSet<Q> data, S value);

    /**
     * Calculates the sum of all elements.
     *
     * @param data the DataSet to operate on.
     * @return the sum of the DataSet.
     * @see IRelevanceScore
     * @see Double
     */
    S sum(DataSet<Q> data);

    /**
     * Calculates the minimum of all elements.
     *
     * @param data the DataSet to operate on.
     * @return the minimum of the DataSet.
     * @see IRelevanceScore
     * @see Double
     */
    S min(DataSet<Q> data);

    /**
     * Calculates the maximum of all elements.
     *
     * @param data the DataSet to operate on.
     * @return the maximum of the DataSet.
     * @see IRelevanceScore
     * @see Double
     */
    S max(DataSet<Q> data);

    /**
     * Getter for a DataSet object by an identifier.
     *
     * @param data       the DataSet to look in.
     * @param identifier the object's identifier to look for.
     * @return the object identified by identifier or null.
     * @see IRelevanceScore
     * @see IDataType
     */
    D getByIdentifier(DataSet<Q> data, long identifier);

}

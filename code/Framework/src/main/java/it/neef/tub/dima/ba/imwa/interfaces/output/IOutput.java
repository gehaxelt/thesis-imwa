package it.neef.tub.dima.ba.imwa.interfaces.output;

import it.neef.tub.dima.ba.imwa.interfaces.calc.ACalculation;
import org.apache.flink.api.java.DataSet;

/**
 * Interface for outputting the result DataSet of a calculation.
 * <p>
 * Created by gehaxelt on 06.12.16.
 */
public interface IOutput<C extends ACalculation, D extends DataSet> {
    /**
     * When a calculation object is passed, the calculation.getResult() DataSet
     * should be outputted.
     *
     * @param calculation the Calculation who's DataSet should be outputted.
     */
    void output(C calculation);

    /**
     * When a DataSet is passed, it is outputted.
     *
     * @param DataSet the DataSet which should be outputted.
     */
    void output(D DataSet);
}

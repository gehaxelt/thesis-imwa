package it.neef.tub.dima.ba.imwa.interfaces.calc;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDataType;

/**
 * Abstract class for a Calculation which takes care of running all
 * processing steps in the right order.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public abstract class ACalculation<T extends IDataType, S extends Double, B extends IDataType, A extends IArgumentsBundle> implements ICalculation<T, S, B, A> {

    /**
     * Runs all processing steps in the right order.
     *
     * @see ICalculation#run()
     */
    @Override
    public void run() {
        this.init();
        this.preProcess();
        this.process();
        this.postProcess();
    }
}

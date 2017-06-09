package it.neef.tub.dima.ba.imwa.impl.output;

import it.neef.tub.dima.ba.imwa.interfaces.calc.ACalculation;
import it.neef.tub.dima.ba.imwa.interfaces.output.IOutput;
import org.apache.flink.api.java.DataSet;

/**
 * Simple class implementing the IOutput interface which prints
 * the DataSets on the console.
 * <p>
 * <p>
 * Created by gehaxelt on 06.12.16.
 */
public class ConsoleOutput implements IOutput {
    @Override
    public void output(ACalculation calculation) {
        // If we get a calculation, we most likely want to print the result DataSet.
        if (calculation.getResult() == null) {
            return;
        }
        try {
            calculation.getResult().print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void output(DataSet dataSet) {
        if (dataSet == null) {
            return;
        }
        try {
            dataSet.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;

/**
 * Difference class based on Double
 * <p>
 * Created by gehaxelt on 12.01.17.
 */
public class DoubleDifference implements IDifference<Double> {

    /**
     * The difference value. Default is Double.MIN_VALUE
     */
    private Double difference = null ; //Double.MIN_VALUE;

    @Override
    public Double getDifference() {
        return this.difference;
    }

    @Override
    public void setDifference(Double difference) {
        this.difference = difference;
    }

    @Override
    public long getIdentifier() {
        return this.hashCode();
    }

    @Override
    public String toString() {
        return this.difference.toString();
    }
}
package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;

/**
 * Difference class based on Integer values.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class IntegerDifference implements IDifference<Integer> {

    /**
     * The difference's value. Default is Integer.MIN_VALUE
     */
    private Integer difference = Integer.MIN_VALUE;

    @Override
    public Integer getDifference() {
        return this.difference;
    }

    @Override
    public void setDifference(Integer difference) {
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

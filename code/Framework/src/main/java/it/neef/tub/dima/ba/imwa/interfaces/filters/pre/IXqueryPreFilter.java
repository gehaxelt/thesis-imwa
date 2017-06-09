package it.neef.tub.dima.ba.imwa.interfaces.filters.pre;

/**
 * Interface for a PreFilter based on Xquery expressions.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public interface IXqueryPreFilter extends IPreFilter {

    /**
     * Getter for the Xquery string.
     *
     * @return the PreFilter's Xquery string.
     */
    String getXquery();

    /**
     * Setter for the PreFilter's Xquery string.
     *
     * @param xquery the new Xquery filter string.
     */
    void setXquery(String xquery);
}

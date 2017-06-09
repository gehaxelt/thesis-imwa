package it.neef.tub.dima.ba.imwa.interfaces.filters.pre;

/**
 * Interface for a PreFilter using Xpath.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public interface IXpathPreFilter extends IPreFilter {

    /**
     * Getter for the PreFilter's Xpath.
     *
     * @return the current Xpath filter string.
     */
    String getXpath();

    /**
     * Setter for the PreFilter's Xpath.
     *
     * @param xpath the new Xpath filter string.
     */
    void setXpath(String xpath);
}

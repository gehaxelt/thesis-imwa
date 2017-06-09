package it.neef.tub.dima.ba.imwa.interfaces.filters.pre;

import java.io.Serializable;

/**
 * Interface for a PreFilter class. The Filter is applied prior to the parsing
 * of a wikipedia page, thus getting the full page's XML string to examine.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IPreFilter extends Serializable {
    /**
     * Filters wikipedia pages before they are parsed based on their XML data.
     *
     * @param pageXML the page's XML representation
     * @return true if page should be further processed. false if it should be discarded.
     */
    boolean filter(String pageXML);
}

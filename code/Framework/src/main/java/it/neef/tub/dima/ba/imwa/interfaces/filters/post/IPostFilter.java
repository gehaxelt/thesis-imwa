package it.neef.tub.dima.ba.imwa.interfaces.filters.post;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;

import java.io.Serializable;

/**
 * Interface for a PostFilter class. It is used to filter pages after they have been
 * parsed. This has the advantage of having access to the raw parsed information.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IPostFilter<P extends IPage> extends Serializable {

    /**
     * Filter page objects based on the parsed information.
     *
     * @param page the page to check for its validity.
     * @return true if the page should be processed. false if the page should be discarded.
     */
    boolean filter(P page);
}

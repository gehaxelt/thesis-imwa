package it.neef.tub.dima.ba.imwa.interfaces.data;

import java.util.List;

/**
 * Interface for Page objects.
 * Page objects should contain information about a wiki's wikipedia page.
 * The pages of a wiki are populated while parsing a <a href="http://dumps.wikimedia.org/">wikipedia dump</a>
 * This information consists of the page title, a page ID, a namespace, a pageview object,
 * restrictions and a list of revisions.
 * A wikipedia page is built upon revisions and the latest revision leads to the current version
 * of a page.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IPage<R extends IRevision, V extends IPageview> extends IDataType {

    /**
     * Getter for the page's title.
     * Every wikipedia page has an unique title in their respective wikipedia.
     * The title should be normalized.
     *
     * @return the page's title.
     */
    String getTitle();

    /**
     * Setter for the page's title.
     *
     * @param title the new title.
     * @see #getTitle()
     */
    void setTitle(String title);

    /**
     * Getter for the page's ID.
     * Every page has an unique ID in their respective wikipedia.
     *
     * @return the page's ID.
     */
    int getID();

    /**
     * Setter for the page's ID.
     * The value should be positive.
     *
     * @param ID the new ID.
     */
    void setID(int ID);

    /**
     * Getter for the page's name space.
     * Namespaces are used to separate different areas of a wikipedia page.
     *
     * @return the page's namespace.
     */
    int getNameSpace();

    /**
     * Setter for the page's namespace.
     * The value must be positive.
     *
     * @param nameSpace the new namespace.
     */
    void setNameSpace(int nameSpace);

    /**
     * Getter for the page's restrictions.
     *
     * @return the page's restrictions.
     */
    String getRestrictions();

    /**
     * Setter for the page's restrictions.
     *
     * @param restrictions the new restrictions.
     */
    void setRestrictions(String restrictions);

    /**
     * Getter for the page's list of revisions.
     * This list is the history of changes to the page. The latest
     * revision is the current version of the page.
     *
     * @return a list of the page's revisions.
     * @see IRevision
     */
    List<R> getRevisions();

    /**
     * Setter for the page's list of revisions.
     * The list should contain the revisions of the page.
     *
     * @param revisions the new list of revisions.
     * @see IRevision
     */
    void setRevisions(List<R> revisions);

    /**
     * Getter for the page's pageview object.
     * If pageview data was parsed and a matching page title found,
     * this will return a pageview object. Otherwise null.
     *
     * @return the page's pageview object.
     * @see IPageview
     */
    V getPageview();

    /**
     * Setter for the page's pageview object.
     *
     * @param pageview the new pageview object.
     * @see IPageview
     */
    void setPageview(V pageview);

    /**
     * This method is called after the parsing of the object has finished.
     */
    void postParsing();
}

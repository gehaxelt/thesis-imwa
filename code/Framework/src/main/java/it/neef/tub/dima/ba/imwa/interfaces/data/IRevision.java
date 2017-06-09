package it.neef.tub.dima.ba.imwa.interfaces.data;

/**
 * Interface for a Revision class.
 * It stores information about a wikipedia revision, such as the revision's ID, parentID, text.
 * Also references to the preceding revision (parentRevision) and following revision (childRevision) are
 * also included as well as a reference to the contributor object.
 * A revision is used to denote a change in a wikipedia page.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IRevision<P extends IPage, R extends IRevision, C extends IContributor, D extends IDifference> extends IDataType {

    /**
     * Getter for the revision ID.
     *
     * @return the revision ID.
     */
    int getID();

    /**
     * Setter for the revision ID.
     *
     * @param ID the new ID.
     */
    void setID(int ID);

    /**
     * Getter for the revision's parent ID.
     * This ID should be the same as the revision ID of the parent revision.
     *
     * @return the parent ID.
     */
    int getParentID();

    /**
     * Setter for the revision's parent ID.
     *
     * @param parentID the new parent ID.
     * @see #getParentID
     */
    void setParentID(int parentID);

    /**
     * Getter for the revision's within-page ID.
     * This ID is the relative position of the revision in a page.
     * E.g. the first revision of a page has ID 0.
     *
     * @return the revision's within-page ID.
     */
    int getWithInPageID();

    /**
     * Setter for the revision's within-page ID.
     *
     * @param withInPageID the new within-page ID.
     * @see #getWithInPageID
     */
    void setWithInPageID(int withInPageID);

    /**
     * Getter for the parent revision object.
     * The parent revision is the revision which precedes the current revision.
     *
     * @return the parent revision object or null if there is none.
     * @see IRevision
     */
    R getParentRevision();

    /**
     * Setter for the parent revision object.
     *
     * @param parentRevision the new parent revision.
     * @see IRevision
     */
    void setParentRevision(R parentRevision);

    /**
     * Getter for the child revision which follows the current revision.
     *
     * @return the child revision object or null if there is none.
     * @see IRevision
     */
    R getChildRevision();

    /**
     * Setter for the child revision object.
     *
     * @param childRevision the new child revision.
     * @see IRevision
     */
    void setChildRevision(R childRevision);

    /**
     * Getter for the Difference object, which should contain the
     * differences between this and the child revision.
     *
     * @return the difference object for this revision.
     * @see IDifference
     */
    D getDifference();

    /**
     * Setter for the difference object.
     *
     * @param difference the new difference.
     * @see #getDifference
     * @see IDifference
     */
    void setDifference(D difference);

    /**
     * Getter for this revision's text.
     * The text is the new version of the corresponding wikipedia page.
     *
     * @return the revision's text.
     */
    String getText();

    /**
     * Setter for the revision's text.
     *
     * @param text the new text.
     */
    void setText(String text);

    /**
     * Getter for the revision's contributor object.
     *
     * @return the revision's contributor object.
     * @see IContributor
     */
    C getContributor();

    /**
     * Setter for the revision's contributor object.
     *
     * @param IContributor the new contributor.
     * @see IContributor
     */
    void setContributor(C IContributor);

    /**
     * Getter for the revision's page object.
     *
     * @return the revision's page object.
     * @see IPage
     */
    P getPage();

    /**
     * Setter for the revision's page object.
     *
     * @param IPage the new page.
     * @see IPage
     */
    void setPage(P IPage);

    /**
     * This method is called after the parsing of the object has finished.
     */
    void postParsing();
}

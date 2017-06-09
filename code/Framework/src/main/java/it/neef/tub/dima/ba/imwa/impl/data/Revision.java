package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;

/**
 * Revision class for information about Wikipedia Revisions.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class Revision implements IRevision {

    /**
     * The revision's ID.
     */
    private int ID = -1;

    /**
     * The revision's parent ID.
     */
    private int parentID = -1;

    /**
     * The relative ID within the page.
     */
    private int withInPageID = -1;

    /**
     * The revision's contents.
     */
    private String text;

    /**
     * The parent revision (predecessor).
     */
    private IRevision parentRevision = null;

    /**
     * The child revision (successor).
     */
    private IRevision childRevision = null;

    /**
     * The revision's contributor.
     */
    private IContributor contributor = null;

    /**
     * The revision's difference to another revision (usually the predecessor)
     */
    private IDifference difference = null;

    /**
     * The revision's associated page.
     */
    private IPage page = null;


    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getParentID() {
        return this.parentID;
    }

    @Override
    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    @Override
    public int getWithInPageID() {
        return this.withInPageID;
    }

    @Override
    public void setWithInPageID(int withInPageID) {
        this.withInPageID = withInPageID;
    }

    @Override
    public IRevision getParentRevision() {
        return this.parentRevision;
    }

    @Override
    public void setParentRevision(IRevision parentRevision) {
        this.parentRevision = parentRevision;
    }

    @Override
    public IRevision getChildRevision() {
        return childRevision;
    }

    @Override
    public void setChildRevision(IRevision childRevision) {
        this.childRevision = childRevision;
    }

    @Override
    public IDifference getDifference() {
        return this.difference;
    }

    @Override
    public void setDifference(IDifference difference) {
        this.difference = difference;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public IContributor getContributor() {
        return this.contributor;
    }

    @Override
    public void setContributor(IContributor contributor) {
        this.contributor = contributor;
    }

    @Override
    public IPage getPage() {
        return this.page;
    }

    @Override
    public void setPage(IPage page) {
        this.page = page;
    }

    @Override
    public void postParsing() {

    }

    @Override
    public long getIdentifier() {
        return 32 * this.getID() + 5;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getID()) + " (" + String.valueOf(this.withInPageID) + ") by: " + this.getContributor().getUsername() + " / " + this.getContributor().getIP();
        //return String.valueOf(this.getID()) + " (" + String.valueOf(this.withInPageID) + ") by: " + this.getContributor().getUsername() + " text: " + this.getText();
    }
}

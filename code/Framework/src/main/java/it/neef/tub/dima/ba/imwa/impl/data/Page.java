package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for information about Wikipedia Pages.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class Page implements IPage<IRevision, IPageview> {

    /**
     * The page's title.
     */
    private String title = null;

    /**
     * The page's ID.
     */
    private int ID = -1;

    /**
     * The page's namespace.
     */
    private int nameSpace = -1;

    /**
     * The page's restrictions.
     */
    private String restrictions = null;

    /**
     * The list of the page's revisions.
     */
    private List<IRevision> revisions = new ArrayList<>();

    /**
     * The page's pageview statistics.
     */
    private IPageview pageview = null;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getNameSpace() {
        return this.nameSpace;
    }

    @Override
    public void setNameSpace(int nameSpace) {
        this.nameSpace = nameSpace;
    }

    @Override
    public String getRestrictions() {
        return this.restrictions;
    }

    @Override
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public List<IRevision> getRevisions() {
        return this.revisions;
    }

    @Override
    public void setRevisions(List<IRevision> revisions) {
        this.revisions = revisions;
    }

    @Override
    public IPageview getPageview() {
        return this.pageview;
    }

    @Override
    public void setPageview(IPageview pageview) {
        this.pageview = pageview;
    }

    @Override
    public void postParsing() {

    }

    @Override
    public long getIdentifier() {
        return this.hashCode();
    }

    @Override
    public String toString() {
        // If no pageview information are available, set them to -1.
        int pageviews = -1;
        if (this.pageview != null) {
            pageviews = this.pageview.getRequestCount();
        }
        return String.valueOf(this.nameSpace) + ", " + String.valueOf(this.getID()) + ", " + this.title + ", " + String.valueOf(pageviews);
    }
}

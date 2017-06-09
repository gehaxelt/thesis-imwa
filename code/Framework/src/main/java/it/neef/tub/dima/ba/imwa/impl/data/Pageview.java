package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;

/**
 * Class holding information about a Wikipedia page's pageview statistics.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public class Pageview implements IPageview {

    /**
     * The corresponding Wikipedia's project name.
     */
    private String projectName = null;

    /**
     * The corresponding Wikipedia's page title.
     */
    private String pageTitle = null;

    /**
     * The Wikipedia page's total request count.
     */
    private int requestCount = -1;

    /**
     * The Wikipedia's page total request size.
     */
    private int requestSize = -1;

    @Override
    public String getProjectName() {
        return this.projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getPageTitle() {
        return this.pageTitle;
    }

    @Override
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public int getRequestCount() {
        return this.requestCount;
    }

    @Override
    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public int getRequestSize() {
        return this.requestSize;
    }

    @Override
    public void setRequestSize(int requestSize) {
        this.requestSize = requestSize;
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
        return this.projectName + ", " + this.pageTitle + ", " + String.valueOf(this.requestCount) + ", " + String.valueOf(this.requestSize);
    }
}

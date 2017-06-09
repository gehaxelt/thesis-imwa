package it.neef.tub.dima.ba.imwa.interfaces.data;

/**
 * Interface for a Pageview class.
 * The Pageview objects should contain information about a wikiepdia page's pageview count.
 * This data can be obtained from pagecounts dataset: the <a href="https://wikitech.wikimedia.org/wiki/Analytics/Data/Pageviews">Pageviews DataSet</a>
 * In particular each object has information about the associated project name "domain_code", the
 * requested page title "page_title", the respective request count "count_views" and request size "total_response_size".
 * This information might be useful to estimate the popularity of a page or revision and thus the
 * change's exposure.
 * <p>
 * Created by gehaxelt on 09.10.16.
 */
public interface IPageview extends IDataType {

    /**
     * The pageview's abbreviated associated project name.
     * E.g. "en" for en.wikipedia.org
     *
     * @return the project's abbreviation.
     * @see <a href="https://wikitech.wikimedia.org/wiki/Analytics/Data/Pageviews">Pageviews Wikipage</a>
     */
    String getProjectName();

    /**
     * Setter for the abbreviated project name.
     *
     * @param projectName the new projectName.
     * @see #getProjectName()
     */
    void setProjectName(String projectName);

    /**
     * Getter for the pageview's page title.
     * The title is normalized, so that it matches the page title
     * from the wikipedia dumps.
     *
     * @return the pageview's page title.
     */
    String getPageTitle();

    /**
     * Setter for the pageview's page title.
     * The new page title should be normalized, so that it matches the page title
     * from the wikipedia dumps.
     *
     * @param pageTitle the new normalized page title.
     */
    void setPageTitle(String pageTitle);

    /**
     * Getter for the pageview's request count.
     * This is the amount of request a page had.
     *
     * @return the request count.
     */
    int getRequestCount();

    /**
     * Setter for the pageview's request count.
     * The number should always be positive.
     *
     * @param requestCount the new request count.
     */
    void setRequestCount(int requestCount);

    /**
     * Getter for the total request size to that page.
     *
     * @return the total request size.
     */
    int getRequestSize();

    /**
     * Setter for the total request size.
     * The value should always be positive.
     *
     * @param requestSize the new total request size.
     */
    void setRequestSize(int requestSize);

    /**
     * This method is called after the parsing of the object has finished.
     */
    void postParsing();
}

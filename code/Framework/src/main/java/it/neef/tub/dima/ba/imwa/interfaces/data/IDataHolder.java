package it.neef.tub.dima.ba.imwa.interfaces.data;


import it.neef.tub.dima.ba.imwa.interfaces.calc.ICalculation;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IInputParser;
import org.apache.flink.api.java.DataSet;

import java.io.Serializable;

/**
 * Interface for a DataHolder class.
 * The DataHolder should have references to all major DataSets {@link IPage}, {@link IRevision},
 * {@link IContributor} after the {@link IInputParser} finished its work.
 * This should further be used to feed the {@link ICalculation} classes.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public interface IDataHolder<P extends IPage, R extends IRevision, C extends IContributor> extends Serializable {

    /**
     * Getter for the page DataSet.
     *
     * @return DataSet containing pages.
     * @see IPage
     */
    DataSet<P> getPages();

    /**
     * Setter for the page DataSet.
     *
     * @param pages the new page DataSet.
     * @see IPage
     */
    void setPages(DataSet<P> pages);

    /**
     * Adds a page to the page DataSet.
     *
     * @param page the page to add.
     * @see IPage
     */
    void addPage(P page);

    /**
     * Removes a page from the DataSet by its pageID.
     *
     * @param pageID the page ID of the page to remove.
     * @throws Exception If an error occurs while removing the page.
     * @see IPage
     */
    void removePageByID(final int pageID) throws Exception;

    /**
     * Getter for a page from the page DataSet by ID.
     * Note: This might user .count() or .collect() and thus trigger a job submission. This might
     * impact the overall performance.
     *
     * @param pageID the page to look for.
     * @return the page object if found. null otherwise.
     * @throws Exception If an error occurs while getting the page.
     * @see IPage
     */
    P getPageByID(final int pageID) throws Exception;

    /**
     * Getter for the revision DataSet.
     *
     * @return the revision DataSet
     * @see IRevision
     */
    DataSet<R> getRevisions();

    /**
     * Setter for the revision DataSet.
     *
     * @param revisions the new revision DataSet.
     * @see IRevision
     */
    void setRevisions(DataSet<R> revisions);

    /**
     * Adds a revision to the revision DataSet.
     *
     * @param revision the revision to add.
     * @see IRevision
     */
    void addRevision(R revision);

    /**
     * Removes a revision by its revision ID.
     *
     * @param revisionID the ID of the revision which should be removed.
     * @throws Exception If an error occurs while removing the revision.
     * @see IRevision
     */
    void removeRevisionByID(final int revisionID) throws Exception;

    /**
     * Getter for a revision by id.
     * Note: This might user .count() or .collect() and thus trigger a job submission. This might
     * impact the overall performance.
     *
     * @param revisionID the ID of the revision to look for.
     * @return the revision object or null if it cannot be found.
     * @throws Exception If an error occurs while getting the revision.
     * @see IRevision
     */
    R getRevisionByID(final int revisionID) throws Exception;

    /**
     * Getter for the contributor DataSet.
     *
     * @return the contributor DataSet
     * @see IContributor
     */
    DataSet<C> getContributors();

    /**
     * Setter for the contributor DataSet.
     *
     * @param contributors the new contributor DataSet.
     * @see IContributor
     */
    void setContributors(DataSet<C> contributors);

    /**
     * Adds a contributor to the contributor DataSet.
     *
     * @param contributor the contributor to add.
     * @see IContributor
     */
    void addContributor(C contributor);

    /**
     * Removes a contributor by ID.
     *
     * @param contributorID the ID of the contributor to remove.
     * @throws Exception If an error occurs while removing the contributor.
     * @see IContributor
     */
    void removeContributorByID(final int contributorID) throws Exception;

    /**
     * Removes a contributor by its username.
     *
     * @param username the username of the contributor to remove.
     * @throws Exception If an error occurs while removing the contributor.
     * @see IContributor
     */
    void removeContributorByUsername(final String username) throws Exception;

    /**
     * Getter for a contributor by her ID.
     * Note: This might user .count() or .collect() and thus trigger a job submission. This might
     * impact the overall performance.
     *
     * @param contributorID the ID of the contributor to find.
     * @return the contributor object or null if it does not exist.
     * @throws Exception If an error occurs while getting the contributor.
     * @see IContributor
     */
    C getContributorByID(final int contributorID) throws Exception;

    /**
     * Getter for a contributor by her username.
     * Note: This might user .count() or .collect() and thus trigger a job submission. This might
     * impact the overall performance.
     *
     * @param username the username of the contributor to find.
     * @return the contributor object or null if it does not exist.
     * @throws Exception If an error occurs while getting the contributor.
     * @see IContributor
     */
    C getContributorByUsername(final String username) throws Exception;

}

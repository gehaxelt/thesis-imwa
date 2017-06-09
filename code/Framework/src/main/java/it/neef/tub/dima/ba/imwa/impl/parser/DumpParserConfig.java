package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IContributorFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IPageFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.IRevisionFactory;

import java.io.Serializable;

/**
 * This class is used as a configuration class for the DumpParsers, because
 * otherwise there are errors when initiating the factories.
 * <p>
 * Created by gehaxelt on 23.10.16.
 */
public class DumpParserConfig implements Serializable {

    /**
     * The IContributor object factory.
     */
    private IContributorFactory iContributorFactory;

    /**
     * The IPage object factory.
     */
    private IPageFactory iPageFactory;

    /**
     * The IRevision factory.
     */
    private IRevisionFactory iRevisionFactory;

    /**
     * Contructor which takes and sets the three different factories.
     *
     * @param iPageFactory        the IPage object factory to use
     * @param iRevisionFactory    the IRevision object factory to use
     * @param iContributorFactory the IContributor object factory to use
     */
    public DumpParserConfig(IPageFactory iPageFactory, IRevisionFactory iRevisionFactory, IContributorFactory iContributorFactory) {
        this.iPageFactory = iPageFactory;
        this.iRevisionFactory = iRevisionFactory;
        this.iContributorFactory = iContributorFactory;
    }

    /**
     * Getter for the IContributorFactory object.
     *
     * @return an instance of the IContributorFactory
     * @see IContributorFactory
     */
    public IContributorFactory getContributorFactory() {
        return iContributorFactory;
    }


    /**
     * Setter for the IContributorFactory object
     *
     * @param iContributorFactory the new IContributor factory.
     * @see IContributorFactory
     */
    public void setContributorFactory(IContributorFactory iContributorFactory) {
        this.iContributorFactory = iContributorFactory;
    }

    /**
     * Getter for the IPageFactory object.
     *
     * @return an instance of the IPageFactory
     * @see IPageFactory
     */
    public IPageFactory getPageFactory() {
        return iPageFactory;
    }

    /**
     * Setter for the IPageFactory object
     *
     * @param iPageFactory the new IPage factory.
     * @see IPageFactory
     */
    public void setPageFactory(IPageFactory iPageFactory) {
        this.iPageFactory = iPageFactory;
    }

    /**
     * Getter for the IRevisionFactory object.
     *
     * @return an instance of the IRevisionFactory
     * @see IRevisionFactory
     */
    public IRevisionFactory getRevisionFactory() {
        return iRevisionFactory;
    }

    /**
     * Setter for the IRevisionFactory object
     *
     * @param iRevisionFactory the new IRevision factory.
     * @see IRevisionFactory
     */
    public void setRevisionFactory(IRevisionFactory iRevisionFactory) {
        this.iRevisionFactory = iRevisionFactory;
    }
}

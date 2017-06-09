package it.neef.tub.dima.ba.imwa.impl.data;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDataHolder;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;

/**
 * DataHolder class for providing the page/revision/contributor DataSets.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class DataHolder implements IDataHolder<IPage, IRevision, IContributor> {

    /**
     * The final page DataSet containing all pages.
     */
    private DataSet<IPage> iPageDataSet = null;

    /**
     * the final revision DataSet containing all revisions.
     */
    private DataSet<IRevision> iRevisionDataSet = null;

    /**
     * The final contributor DataSet containing all contributors.
     */
    private DataSet<IContributor> iContributorDataSet = null;

    @Override
    public DataSet<IPage> getPages() {
        return this.iPageDataSet;
    }

    @Override
    public void setPages(DataSet<IPage> pages) {
        this.iPageDataSet = pages;
    }

    @Override
    public void addPage(IPage page) {
        this.iPageDataSet = this.iPageDataSet.union(Framework.getInstance().getConfiguration().getExecutionEnvironment().fromElements(page));
    }

    @Override
    public void removePageByID(final int pageID) throws Exception {
        this.iPageDataSet = this.iPageDataSet.filter(new FilterFunction<IPage>() {
            @Override
            public boolean filter(IPage page) throws Exception {
                return page.getID() != pageID;
            }
        });
    }

    @Override
    public IPage getPageByID(final int pageID) throws Exception {
        try {
            // We have to filter through the whole DataSet to remove one element.
            DataSet<IPage> subPages = this.iPageDataSet.filter(new FilterFunction<IPage>() {
                @Override
                public boolean filter(IPage page) throws Exception {
                    return page.getID() == pageID;
                }
            });
            // The collect triggers a job execution and therefor decreases performance.
            return subPages.collect().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataSet<IRevision> getRevisions() {
        return this.iRevisionDataSet;
    }

    @Override
    public void setRevisions(DataSet<IRevision> revisions) {
        this.iRevisionDataSet = revisions;
    }

    @Override
    public void addRevision(IRevision revision) {
        this.iRevisionDataSet = this.iRevisionDataSet.union(Framework.getInstance().getConfiguration().getExecutionEnvironment().fromElements(revision));
    }

    @Override
    public void removeRevisionByID(final int revisionID) throws Exception {
        this.iRevisionDataSet = this.iRevisionDataSet.filter(new FilterFunction<IRevision>() {
            @Override
            public boolean filter(IRevision revision) throws Exception {
                return revision.getID() != revisionID;
            }
        });
    }

    @Override
    public IRevision getRevisionByID(final int revisionID) throws Exception {
        try {
            // We have to filter through the whole DataSet to remove one element.
            DataSet<IRevision> subRevisions = this.iRevisionDataSet.filter(new FilterFunction<IRevision>() {
                @Override
                public boolean filter(IRevision revision) throws Exception {
                    return revision.getID() == revisionID;
                }
            });
            // The collect triggers a job execution and therefor decreases performance.
            return subRevisions.collect().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataSet<IContributor> getContributors() {
        return this.iContributorDataSet;
    }

    @Override
    public void setContributors(DataSet<IContributor> contributors) {
        this.iContributorDataSet = contributors;
    }

    @Override
    public void addContributor(IContributor contributor) {
        this.iContributorDataSet = this.iContributorDataSet.union(Framework.getInstance().getConfiguration().getExecutionEnvironment().fromElements(contributor));
    }

    @Override
    public void removeContributorByID(final int contributorID) throws Exception {
        this.iContributorDataSet = this.iContributorDataSet.filter(new FilterFunction<IContributor>() {
            @Override
            public boolean filter(IContributor contributor) throws Exception {
                return contributor.getID() != contributorID;
            }
        });
    }

    @Override
    public void removeContributorByUsername(final String username) throws Exception {
        this.iContributorDataSet = this.iContributorDataSet.filter(new FilterFunction<IContributor>() {
            @Override
            public boolean filter(IContributor contributor) throws Exception {
                if (username == null) {
                    return contributor.getUsername() != null;
                } else {
                    return ! contributor.getUsername().equals(username);
                }
            }
        });
    }

    @Override
    public IContributor getContributorByID(final int contributorID) throws Exception {
        try {
            // We have to filter through the whole DataSet to remove one element.
            DataSet<IContributor> subContributors = this.iContributorDataSet.filter(new FilterFunction<IContributor>() {
                @Override
                public boolean filter(IContributor contributor) throws Exception {
                    return contributor.getID() == contributorID;
                }
            });
            // The collect triggers a job execution and therefor decreases performance.
            return subContributors.collect().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IContributor getContributorByUsername(final String username) throws Exception {
        try {
            // We have to filter through the whole DataSet to remove one element.
            DataSet<IContributor> subContributors = this.iContributorDataSet.filter(new FilterFunction<IContributor>() {
                @Override
                public boolean filter(IContributor contributor) throws Exception {
                    if(username == null) {
                        return contributor.getUsername() == null;
                    } else {
                        return contributor.getUsername().equals(username);
                    }
                }
            });
            // The collect triggers a job execution and therefor decreases performance.
            return subContributors.collect().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

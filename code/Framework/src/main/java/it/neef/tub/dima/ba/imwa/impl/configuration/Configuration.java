package it.neef.tub.dima.ba.imwa.impl.configuration;

import it.neef.tub.dima.ba.imwa.impl.calc.RelevanceAggregator;
import it.neef.tub.dima.ba.imwa.interfaces.calc.IRelevanceAggregator;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDataHolder;
import it.neef.tub.dima.ba.imwa.interfaces.factories.data.*;
import it.neef.tub.dima.ba.imwa.interfaces.factories.diff.IDifferFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.post.ICustomPostFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IRegexPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IXpathPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.filters.pre.IXqueryPreFilterFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.output.IOutputFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IDumpParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IInputParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IPageviewParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IPreFilter;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Framework's configuration class.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class Configuration implements Serializable {

    /**
     * The Contributor object factory.
     */
    private IContributorFactory contributorFactory;

    /**
     * The Difference object factory.
     */
    private IDifferenceFactory differenceFactory;

    /**
     * The Differ object factory.
     */
    private IDifferFactory differFactory;

    /**
     * The Page object factory.
     */
    private IPageFactory pageFactory;

    /**
     * The Pageview object factory.
     */
    private IPageviewFactory pageviewFactory;

    /**
     * The Revision object factory.
     */
    private IRevisionFactory revisionFactory;

    /**
     * The DataHolder object factory.
     */
    private IDataHolderFactory dataHolderFactory;

    /**
     * The RegexPreFilter object factory.
     */
    private IRegexPreFilterFactory regexPreFilterFactory;

    /**
     * The XpathPreFilter object factory.
     */
    private IXpathPreFilterFactory xpathPreFilterFactory;

    /**
     * The XqueryPreFilter object factory.
     */
    private IXqueryPreFilterFactory xqueryPreFilterFactory;

    /**
     * The CustomPostFilter object factory.
     */
    private ICustomPostFilterFactory customPostFilterFactory;

    /**
     * The DumpParser object factory.
     */
    private IDumpParserFactory dumpParserFactory;

    /**
     * The PageviewParser object factory.
     */
    private IPageviewParserFactory pageviewParserFactory;

    /**
     * The InputParser object factory.
     */
    private IInputParserFactory inputParserFactory;

    /**
     * The RelevanceScore object factory.
     */
    private IRelevanceScoreFactory relevanceScoreFactory;

    /**
     * The Output object factory.
     */
    private IOutputFactory outputFactory;

    /**
     * The global DataHolder object.
     */
    private IDataHolder dataHolder;

    /**
     * The global list of PreFilters to apply.
     */
    private List<IPreFilter> preFilters = new ArrayList<>();

    /**
     * The global list of PostFilters to apply.
     */
    private List<IPostFilter> postFilters = new ArrayList<>();

    /**
     * The global RelevanceAggregator object.
     */
    private IRelevanceAggregator relevanceAggregator = new RelevanceAggregator();

    /**
     * The path to the Wikipedia dumps.
     */
    private String wikipediaDumpPath;

    /**
     * The path to the pageview dumps.
     */
    private String pageviewDumpPath;

    /**
     * The Wikipedia's project name as the short tag. (E.g. "en" for the english en.wikipedia.org)
     */
    private String pageviewDumpShortTag;

    /**
     * The Apache Flink's ExecutionEnvironment.
     */
    private ExecutionEnvironment executionEnvironment;


    /**
     * Getter for the Contributor factory.
     *
     * @return the Contributor factory.
     */
    public IContributorFactory getContributorFactory() {
        return contributorFactory;
    }

    /**
     * Setter for the Contributor factory.
     *
     * @param contributorFactory the new Contributor factory.
     */
    public void setContributorFactory(IContributorFactory contributorFactory) {
        this.contributorFactory = contributorFactory;
    }

    /**
     * Getter for the Difference factory.
     *
     * @return the Difference factory.
     */
    public IDifferenceFactory getDifferenceFactory() {
        return differenceFactory;
    }

    /**
     * Setter for the Difference factory.
     *
     * @param differenceFactory the new Difference factory.
     */
    public void setDifferenceFactory(IDifferenceFactory differenceFactory) {
        this.differenceFactory = differenceFactory;
    }

    /**
     * Getter for the Page factory.
     *
     * @return the Page factory.
     */
    public IPageFactory getPageFactory() {
        return pageFactory;
    }

    /**
     * Setter for the Page factory.
     *
     * @param pageFactory the new Page factory.
     */
    public void setPageFactory(IPageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    /**
     * Getter for the Revision factory.
     *
     * @return the Revision factory.
     */
    public IRevisionFactory getRevisionFactory() {
        return revisionFactory;
    }

    /**
     * Setter for the Revision factory.
     *
     * @param revisionFactory the new Revision factory.
     */
    public void setRevisionFactory(IRevisionFactory revisionFactory) {
        this.revisionFactory = revisionFactory;
    }

    /**
     * Getter for the ExecutionEnvironment.
     *
     * @return the ExecutionEnvironment.
     * @see ExecutionEnvironment
     */
    public ExecutionEnvironment getExecutionEnvironment() {
        return executionEnvironment;
    }

    /**
     * Setter for the ExecutionEnvironment.
     *
     * @param executionEnvironment the new ExecutionEnvironment.
     */
    public void setExecutionEnvironment(ExecutionEnvironment executionEnvironment) {
        this.executionEnvironment = executionEnvironment;
    }

    /**
     * Getter for the wikipedia dump path.
     *
     * @return the path to the wikipedia dump to parse.
     */
    public String getWikipediaDumpPath() {
        return wikipediaDumpPath;
    }

    /**
     * Setter for the wikipedia dump path.
     *
     * @param wikipediaDumpPath the new wikipedia dump path.
     */
    public void setWikipediaDumpPath(String wikipediaDumpPath) {
        this.wikipediaDumpPath = wikipediaDumpPath;
    }

    /**
     * Getter for the wikipedia pageview dump path.
     *
     * @return the path to the wikipedia pageview dump path.
     */
    public String getPageviewDumpPath() {
        return pageviewDumpPath;
    }

    /**
     * Setter for the wikipedia pageview dump path.
     *
     * @param pageviewDumpPath the new wikipedia pageview dump path.
     */
    public void setPageviewDumpPath(String pageviewDumpPath) {
        this.pageviewDumpPath = pageviewDumpPath;
    }

    /**
     * Getter for the pageview dump short tag.
     *
     * @return the short tag.
     */
    public String getPageviewDumpShortTag() {
        return pageviewDumpShortTag;
    }

    /**
     * Setter for the pageview dump short tag.
     *
     * @param pageviewDumpShortTag the new pageview short tag.
     */
    public void setPageviewDumpShortTag(String pageviewDumpShortTag) {
        this.pageviewDumpShortTag = pageviewDumpShortTag;
    }

    /**
     * Getter for the DataHolder object.
     *
     * @return the DataHolder
     */
    public IDataHolder getDataHolder() {
        return dataHolder;
    }

    /**
     * Setter for the DataHolder object.
     *
     * @param dataHolder the new DataHolder object.
     */
    public void setDataHolder(IDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    /**
     * Getter for the DataHolder factory.
     *
     * @return the DataHolder factory.
     */
    public IDataHolderFactory getDataHolderFactory() {
        return dataHolderFactory;
    }

    /**
     * Setter for the DataHolder factory.
     *
     * @param dataHolderFactory the new DataHolder factory.
     */
    public void setDataHolderFactory(IDataHolderFactory dataHolderFactory) {
        this.dataHolderFactory = dataHolderFactory;
    }

    /**
     * Getter for the RegexPreFilter factory.
     *
     * @return the RegexPreFilter factory.
     */
    public IRegexPreFilterFactory getRegexPreFilterFactory() {
        return regexPreFilterFactory;
    }

    /**
     * Setter for the RegexPreFilter factory.
     *
     * @param regexPreFilterFactory the new RegexPreFilter factory.
     */
    public void setRegexPreFilterFactory(IRegexPreFilterFactory regexPreFilterFactory) {
        this.regexPreFilterFactory = regexPreFilterFactory;
    }

    /**
     * Getter for the XpathPreFilter factory.
     *
     * @return the XpathPreFilter factory.
     */
    public IXpathPreFilterFactory getXpathPreFilterFactory() {
        return xpathPreFilterFactory;
    }

    /**
     * Setter for the XpathPreFilter factory.
     *
     * @param xpathPreFilterFactory the new XpathPreFilter factory.
     */
    public void setXpathPreFilterFactory(IXpathPreFilterFactory xpathPreFilterFactory) {
        this.xpathPreFilterFactory = xpathPreFilterFactory;
    }

    /**
     * Getter for the XqueryPreFilter factory.
     *
     * @return the XqueryPreFilter factory.
     */
    public IXqueryPreFilterFactory getXqueryPreFilterFactory() {
        return xqueryPreFilterFactory;
    }

    /**
     * Setter for the XqueryPreFilter factory.
     *
     * @param xqueryPreFilterFactory the new XqueryPreFilter factory.
     */
    public void setXqueryPreFilterFactory(IXqueryPreFilterFactory xqueryPreFilterFactory) {
        this.xqueryPreFilterFactory = xqueryPreFilterFactory;
    }

    /**
     * Getter for the CustomPostFilter factory.
     *
     * @return the CustomPostFilter factory.
     */
    public ICustomPostFilterFactory getCustomPostFilterFactory() {
        return customPostFilterFactory;
    }

    /**
     * Setter for the CustomPostFilter factory.
     *
     * @param customPostFilterFactory the new CustomPostFilter factory.
     */
    public void setCustomPostFilterFactory(ICustomPostFilterFactory customPostFilterFactory) {
        this.customPostFilterFactory = customPostFilterFactory;
    }

    /**
     * Getter for the DumpParser factory.
     *
     * @return the DumpParser factory.
     */
    public IDumpParserFactory getDumpParserFactory() {
        return dumpParserFactory;
    }

    /**
     * Setter for the DumpParser factory.
     *
     * @param dumpParserFactory the new DumpParser factory.
     */
    public void setDumpParserFactory(IDumpParserFactory dumpParserFactory) {
        this.dumpParserFactory = dumpParserFactory;
    }

    /**
     * Getter for the PageviewParser factory.
     *
     * @return the PageviewParser factory.
     */
    public IPageviewParserFactory getPageviewParserFactory() {
        return pageviewParserFactory;
    }

    /**
     * Setter for the PageviewParser factory.
     *
     * @param pageviewParserFactory the new PageviewParser factory.
     */
    public void setPageviewParserFactory(IPageviewParserFactory pageviewParserFactory) {
        this.pageviewParserFactory = pageviewParserFactory;
    }

    /**
     * Getter for the InputParser factory.
     *
     * @return the InputParser factory.
     */
    public IInputParserFactory getInputParserFactory() {
        return inputParserFactory;
    }

    /**
     * Setter for the InputParser factory.
     *
     * @param inputParserFactory the new InputParser factory.
     */
    public void setInputParserFactory(IInputParserFactory inputParserFactory) {
        this.inputParserFactory = inputParserFactory;
    }

    /**
     * Getter for the list of PreFilters.
     *
     * @return the list of PreFilters.
     */
    public List<IPreFilter> getPreFilters() {
        return preFilters;
    }

    /**
     * Setter for the list of PreFilters.
     *
     * @param preFilters the new list of PreFilters.
     */
    public void setPreFilters(List<IPreFilter> preFilters) {
        this.preFilters = preFilters;
    }

    /**
     * Getter for the list of PostFilters.
     *
     * @return the list of PostFilters.
     */
    public List<IPostFilter> getPostFilters() {
        return postFilters;
    }

    /**
     * Setter for the list of PostFilters.
     *
     * @param postFilters the new list of PostFilters.
     */
    public void setPostFilters(List<IPostFilter> postFilters) {
        this.postFilters = postFilters;
    }

    /**
     * Getter for the RelevanceScore factory.
     *
     * @return the RelevanceScore factory.
     */
    public IRelevanceScoreFactory getRelevanceScoreFactory() {
        return relevanceScoreFactory;
    }

    /**
     * Setter for the RelevanceScore factory.
     *
     * @param iRelevanceScoreFactory the new RelevanceScore factory.
     */
    public void setRelevanceScoreFactory(IRelevanceScoreFactory iRelevanceScoreFactory) {
        this.relevanceScoreFactory = iRelevanceScoreFactory;
    }

    /**
     * Getter for the RelevanceScore aggregator.
     *
     * @return the RelevanceScore aggregator.
     */
    public IRelevanceAggregator getRelevanceAggregator() {
        return relevanceAggregator;
    }

    /**
     * Setter for the RelevanceScore aggregator.
     *
     * @param iRelevanceAggregator the new RelevanceScore aggregator.
     */
    public void setRelevanceAggregator(IRelevanceAggregator iRelevanceAggregator) {
        this.relevanceAggregator = iRelevanceAggregator;
    }

    /**
     * Getter for the Difference factory.
     *
     * @return the Difference factory.
     */
    public IDifferFactory getDifferFactory() {
        return differFactory;
    }

    /**
     * Setter for the Difference factory.
     *
     * @param differFactory the new Difference factory.
     */
    public void setDifferFactory(IDifferFactory differFactory) {
        this.differFactory = differFactory;
    }

    /**
     * Getter for the Output factory.
     *
     * @return the Output factory.
     */
    public IOutputFactory getOutputFactory() {
        return outputFactory;
    }

    /**
     * Setter for the Output factory.
     *
     * @param outputFactory the new Output factory.
     */
    public void setOutputFactory(IOutputFactory outputFactory) {
        this.outputFactory = outputFactory;
    }

    /**
     * Getter for the Pageview factory.
     *
     * @return the Pageview factory.
     */
    public IPageviewFactory getPageviewFactory() {
        return pageviewFactory;
    }

    /**
     * Setter for the Pageview factory.
     *
     * @param pageviewFactory the new Pageview factory.
     */
    public void setPageviewFactory(IPageviewFactory pageviewFactory) {
        this.pageviewFactory = pageviewFactory;
    }
}

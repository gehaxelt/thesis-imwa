package it.neef.tub.dima.ba.imwa;

import it.neef.tub.dima.ba.imwa.impl.configuration.Configuration;
import it.neef.tub.dima.ba.imwa.impl.factories.data.*;
import it.neef.tub.dima.ba.imwa.impl.factories.filters.post.CustomPostFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.filters.pre.RegexPreFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.filters.pre.XpathPreFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.filters.pre.XqueryPreFilterFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.output.OutputFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.parser.DumpParserFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.parser.InputParserFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.parser.PageviewParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.calc.ACalculation;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * This is the IMWA-Framework class which is used to configure the framework or to run tasks.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class Framework {
    /**
     * Singleton instance of the framework.
     */
    private final static Framework instance = new Framework();
    /**
     * The framework's configuration object.
     */
    private final Configuration configuration;
    /**
     * A boolean variable to make sure that the input data is only read and parsed once.
     */
    private boolean initFinished = false;

    /**
     * The contructor initializes the default configuration object.
     */
    private Framework() {
        this.configuration = new Configuration();
        this.configuration.setContributorFactory(new ContributorFactory());
        this.configuration.setDifferenceFactory(new DifferenceFactory());
        this.configuration.setPageFactory(new PageFactory());
        this.configuration.setRevisionFactory(new RevisionFactory());
        this.configuration.setPageviewFactory(new PageviewFactory());
        this.configuration.setDataHolderFactory(new DataHolderFactory());
        this.configuration.setRegexPreFilterFactory(new RegexPreFilterFactory());
        this.configuration.setXpathPreFilterFactory(new XpathPreFilterFactory());
        this.configuration.setXqueryPreFilterFactory(new XqueryPreFilterFactory());
        this.configuration.setCustomPostFilterFactory(new CustomPostFilterFactory());
        this.configuration.setDumpParserFactory(new DumpParserFactory());
        this.configuration.setPageviewParserFactory(new PageviewParserFactory());
        this.configuration.setInputParserFactory(new InputParserFactory());
        this.configuration.setRelevanceScoreFactory(new RelevanceScoreFactory());
        this.configuration.setOutputFactory(new OutputFactory());

        this.configuration.setDataHolder(this.configuration.getDataHolderFactory().newDataHolder());
        this.configuration.setExecutionEnvironment(ExecutionEnvironment.getExecutionEnvironment());
        this.configuration.setWikipediaDumpPath(null);
        this.configuration.setPageviewDumpPath(null);
        this.configuration.setPageviewDumpShortTag(null);
    }

    /**
     * Method to return the framework's singleton object.
     *
     * @return Framework the framework's singleton instance
     */
    public static Framework getInstance() {
        return Framework.instance;
    }

    /**
     * Getter for the framework's configuration object.
     *
     * @return the framework's configuration object
     */
    public Configuration getConfiguration() {
        return configuration;
    }


    /**
     * Runs and executes a calculation. If necessary, the input data is parsed first.
     *
     * @param calculation the calculation to run.
     * @throws Exception If an error occurs while processing the calculation.
     */
    public void runCalculation(ACalculation calculation) throws Exception {
        if (!this.initFinished) {
            this.init();
        }
        calculation.run();
    }

    /**
     * Instructs Apache Flink's ExecutionEnvironment to execute using a given job name.
     *
     * @param jobName the current job's name
     * @param execute if true the execution environment is executed. No operation otherwise.
     * @throws Exception If an error occurs while executing a job
     */
    public void run(String jobName, boolean execute) throws Exception {
        if (execute) {
            this.configuration.getExecutionEnvironment().execute(jobName);
        }
    }

    /**
     * Instructs Apache Flink's ExecutionEnvironment to execute using a given job name.
     *
     * @param jobName the current job's name
     * @throws Exception If an error occurs while executing a job
     */
    public void run(String jobName) throws Exception {
        this.run(jobName, true);
    }

    /**
     * Initializes the framework by parsing the wikipedia dumps and pageview.
     * This should only be called once. It sets initFinished to true after completion.
     *
     * @throws Exception If an error occurs while executing the job or the wikipedia or pageviews dump or path is null
     */
    public void init() throws Exception {
        assert (this.configuration.getWikipediaDumpPath() != null);
        //assert(this.configuration.getPageviewDumpPath() != null);

        //Parse Data and stuff
        Framework.getInstance().getConfiguration().getInputParserFactory().newInputParser().run();
        this.initFinished = true;
    }
}

package it.neef.tub.dima.ba.imwa.examples.adlerscm;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity.EditLongevityCalculation;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity.EditLongevityCalculationSingle;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditLongevity.EditLongevityDiffer;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.EditOnly.EditOnlyCalculation;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.NumEdits.NumEditsCalculation;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TenRevisions.TenRevisionsCalculation;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TenRevisions.TenRevisionsCalculationSingle;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevity.TextLongevityCalculation;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevity.TextLongevityCalculationSingle;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevityWithPenalty.TextLongevityWithPenaltyCalculation;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextLongevityWithPenalty.TextLongevityWithPenaltyCalculationSingle;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextOnly.TextOnlyCalculation;
import it.neef.tub.dima.ba.imwa.impl.parser.RegexSkipDumpParser;
import it.neef.tub.dima.ba.imwa.examples.adlerscm.TextOnlyWithPageviews.TextOnlyWithPageviewsCalculation;
import it.neef.tub.dima.ba.imwa.impl.calc.ArgumentsBundle;
import it.neef.tub.dima.ba.imwa.impl.calc.RelevanceAggregator;
import it.neef.tub.dima.ba.imwa.impl.data.*;
import it.neef.tub.dima.ba.imwa.impl.factories.data.*;
import it.neef.tub.dima.ba.imwa.impl.parser.PageviewParser;
import it.neef.tub.dima.ba.imwa.impl.parser.SkipDumpParser;
import it.neef.tub.dima.ba.imwa.impl.parser.SkipXMLContentHandler;
import it.neef.tub.dima.ba.imwa.impl.parser.XmlInputFormat;
import it.neef.tub.dima.ba.imwa.interfaces.calc.ACalculation;
import it.neef.tub.dima.ba.imwa.interfaces.data.IDifference;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import it.neef.tub.dima.ba.imwa.interfaces.diff.IDiffer;
import it.neef.tub.dima.ba.imwa.interfaces.factories.parser.IDumpParserFactory;
import it.neef.tub.dima.ba.imwa.interfaces.filters.post.IPostFilter;
import it.neef.tub.dima.ba.imwa.interfaces.output.IOutput;
import it.neef.tub.dima.ba.imwa.interfaces.parser.IDumpParser;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;


/**
 * Class for running Adler et al. contribution measures.
 * It also serves as an example of how to use the framework.
 * <p>
 * Created by gehaxelt on 25.10.16.
 */
public class AdlerContributionMeasuresExample {

    public void run(String[] args) throws Exception {
        // Initialize the framework and the output objects.
        Framework fw = Framework.getInstance();
        //fw.getConfiguration().setDumpParserFactory(new RegexDumpParserFactory());
        /*
        fw.getConfiguration().getExecutionEnvironment().registerType(ArgumentsBundle.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(RelevanceAggregator.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(Configuration.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(Contributor.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(ContributorRelevanceScore.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(DataHolder.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(DoubleDifference.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(Page.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(Pageview.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(Revision.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(RevisionRelevanceScore.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(ContributorFactory.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(DifferenceFactory.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(PageFactory.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(PageviewFactory.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(RelevanceScoreFactory.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(RevisionFactory.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(SkipDumpParser.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(SkipXMLContentHandler.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(XmlInputFormat.class);
        fw.getConfiguration().getExecutionEnvironment().registerType(PageviewParser.class);
        */
        IOutput outputter = Framework.getInstance().getConfiguration().getOutputFactory().newOutput();
        // Configure the framework to use the correct options.
        fw.getConfiguration().setWikipediaDumpPath(args[0]);
        if(args.length >= 2) {
            fw.getConfiguration().setPageviewDumpPath(args[1]);
        }
        if(args.length >= 3) {
            fw.getConfiguration().setPageviewDumpShortTag(args[2]);
        }
        fw.getConfiguration().getPreFilters().add(fw.getConfiguration().getRegexPreFilterFactory().newIRegexPreFilter("(?is).*<ns>0</ns>.*"));
        //fw.getConfiguration().getPreFilters().add(fw.getConfiguration().getXpathPreFilterFactory().newIXpathPreFilter("/page[ns=\"0\"]"));
        //fw.getConfiguration().getPreFilters().add(fw.getConfiguration().getXqueryPreFilterFactory().newXqueryPreFilter("/page[ns=\"0\"]"));
        //fw.getConfiguration().getPostFilters().add(fw.getConfiguration().getCustomPostFilterFactory().newICustomPostFilter(new TestPostFilter()));
        fw.init();
        //fw.getConfiguration().getDataHolder().getPages().count();
        // Instantiate the Calculation object to run.
        ACalculation calculation = new NumEditsCalculation();
        //ACalculation calculation = new TextOnlyCalculation();
        //ACalculation calculation = new EditOnlyCalculation();
        //ACalculation calculation = new EditLongevityCalculation();
        //ACalculation calculation = new EditLongevityCalculationSingle();
        //ACalculation calculation = new TextLongevityCalculation();
        //ACalculation calculation = new TextLongevityCalculationSingle();
        //ACalculation calculation = new TenRevisionsCalculation();
        //ACalculation calculation = new TenRevisionsCalculationSingle();
        //ACalculation calculation = new TextLongevityWithPenaltyCalculation();
        //ACalculation calculation = new TextLongevityWithPenaltyCalculationSingle();

        // Run and output the calculation.
        fw.runCalculation(calculation);
        outputter.output(calculation);
        //fw.runCalculation(calculation2);
        //outputter.output(calculation2);

    }

    static class TestPostFilter implements IPostFilter {
        @Override
        public boolean filter(IPage page) {
            return page.getNameSpace() == 0;
        }
    }

    static class RegexDumpParserFactory implements IDumpParserFactory {
        @Override
        public IDumpParser newDumpParser() {
            //return new SkipDumpParser();
            return new RegexSkipDumpParser();
        }
    }
}

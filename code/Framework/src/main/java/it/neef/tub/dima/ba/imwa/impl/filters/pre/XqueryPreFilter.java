package it.neef.tub.dima.ba.imwa.impl.filters.pre;

import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXqueryPreFilter;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.*;
import org.xml.sax.InputSource;

import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;


/**
 * PreFilter for filtering with Xqueries.
 * <p>
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class XqueryPreFilter implements IXqueryPreFilter {

    /**
     * The Xquery to use for filtering.
     */
    private String xquery;

    @Override
    public boolean filter(String pageXML) {
        //TODO: Needs testing
        try {
            //Taken from https://stackoverflow.com/questions/15233826/how-do-i-run-an-xquery-against-xml-in-a-string
            // Initialize and compile the Xquery.
            Configuration config = new Configuration();
            Processor processor = new Processor(config);
            XQueryCompiler xqueryCompiler = processor.newXQueryCompiler();
            XQueryExecutable xqueryExec = xqueryCompiler
                    .compile(this.xquery);

            // Create a source from the passed pageXML string.
            XQueryEvaluator xqueryEval = xqueryExec.load();
            xqueryEval.setSource(new SAXSource(new InputSource(
                    new StringReader(pageXML))));

            // Create a destination to save results to and run evaluate the Xquery.
            XdmDestination destination = new XdmDestination();
            xqueryEval.setDestination(destination);
            xqueryEval.run();

            // We only want to know if the Xquery matches the given pageXML. So check if the XdmNode is not empty.
            if (destination.getXdmNode() != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getXquery() {
        return xquery;
    }

    @Override
    public void setXquery(String xquery) {
        this.xquery = xquery;
    }
}

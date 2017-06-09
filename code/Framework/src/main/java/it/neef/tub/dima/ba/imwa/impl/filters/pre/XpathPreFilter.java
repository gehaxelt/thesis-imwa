package it.neef.tub.dima.ba.imwa.impl.filters.pre;

import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IXpathPreFilter;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

/**
 * PreFilter class using Xpath.
 * <p>
 * Created by gehaxelt on 08.10.16.
 */
public class XpathPreFilter implements IXpathPreFilter {

    /**
     * The Xpath string to use as a filter.
     */
    private String xpath;

    @Override
    public boolean filter(String pageXML) {
        // Build source from XML string.
        InputSource source = new InputSource(new StringReader(pageXML));
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            // Evaluate Xpath on the XML string.
            String result = xpath.evaluate(this.xpath, source);
            // Return true if the xpath query has matched.
            if ((result != null) && (!result.equals(""))) {
                return true;
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getXpath() {
        return xpath;
    }

    @Override
    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
}

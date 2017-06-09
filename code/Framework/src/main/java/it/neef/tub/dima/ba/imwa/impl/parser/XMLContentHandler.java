package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.interfaces.data.*;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Class for parsing data from the xml wikipedia dump.
 * <p>
 * Created by gehaxelt on 17.01.16.
 */
public class XMLContentHandler implements ContentHandler {

    /**
     * List of all parsed pages.
     */
    private ArrayList<IPage<IRevision, IPageview>> allPages = new ArrayList<>();
    /**
     * Value between the current tag.
     */
    private String cValue;
    /**
     * Current <page>-tag we are working on.
     */
    private IPage<IRevision, IPageview> cPage;
    /**
     * Current <revision>-tag we are working on.
     */
    private IRevision<IPage, IRevision, IContributor, IDifference> cRevision;

    /**
     * The previously parsed revision, which is the cRevision's parent.
     */
    private IRevision<IPage, IRevision, IContributor, IDifference> parentRevision;

    /**
     * Current <contributor>-tag we are working on.
     */
    private IContributor cContributor;
    /**
     * Current parsing mode.
     */
    private MODES cMode;

    /**
     * The DumpParserConfig containing the necessary factory instances.
     */
    private DumpParserConfig config;

    /**
     * Constructor which sets the config.
     *
     * @param config the config containing factory instances.
     */
    public XMLContentHandler(DumpParserConfig config) {
        this.config = config;
    }

    /**
     * Parses XML Dump from wikipedia. We're only interested in <page>...<revision>...<contributor></contributor></revision></page>
     *
     * @param filePath - Path to file to read XML data from.
     * @return XMLContentHandler with parsed data.
     */
    public static XMLContentHandler parseXML(String filePath, DumpParserConfig config) {
        // Instantiate the XMLContentHandler
        XMLContentHandler xmlHandler = new XMLContentHandler(config);
        try {
            // Setup everything so that we can read the data from the file.
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            FileReader fReader = new FileReader(filePath);
            InputSource iSource = new InputSource(fReader);

            // Set the handler and start the parsing process.
            xmlReader.setContentHandler(xmlHandler);
            xmlReader.parse(iSource);
            fReader.close();

        } catch (IOException e) {
            return null;
        } catch (SAXException e) {
            return null;
        }

        return xmlHandler;
    }

    /**
     * Parses an wikipedia XML dump string with the SkipXMLContentHandler.
     *
     * @param pageXML the XML data to parse.
     * @param config  the config with the object factories.
     * @return the content handler with the parsed Data.
     */
    public static ArrayList<IPage<IRevision, IPageview>> parseXMLString(String pageXML, DumpParserConfig config) {
        XMLContentHandler xmlHandler = new XMLContentHandler(config);
        try {
            // Setup a StringReader to read the stream.
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            InputSource iSource = new InputSource(new StringReader(pageXML));

            // Use the SkipXMLContentHandler to parse the data.
            xmlReader.setContentHandler(xmlHandler);
            xmlReader.parse(iSource);

        } catch (IOException | SAXException e) {
            return null;
        }

        return xmlHandler.getAllPages();
    }

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {
        //Start without a specific mode.
        this.cMode = MODES.NONE;
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String s, String s1) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String s) throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        // Depending on the starting tag, we parse different nodes.
        switch (qName) {
            case "page":
                this.cPage = this.config.getPageFactory().newPage();
                this.cMode = MODES.PAGE;
                // Reset the parentRevision
                this.parentRevision = null;
                break;
            case "revision":
                this.cRevision = this.config.getRevisionFactory().newRevision();
                this.cRevision.setPage(this.cPage);
                this.cMode = MODES.REVISION;
                break;
            case "contributor":
                this.cContributor = this.config.getContributorFactory().newContributor();
                this.cMode = MODES.CONTRIBUTOR;
                break;
            default:
                // We don't care about other starting tags. Just let them pass.
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // After an ending tag has been found, parse and assign the value to a given object.
        switch (this.cMode) {
            case PAGE:
                switch (qName) {
                    case "id":
                        this.cPage.setID(Integer.valueOf(this.cValue));
                        break;
                    case "title":
                        this.cPage.setTitle(this.cValue);
                        break;
                    case "ns":
                        this.cPage.setNameSpace(Integer.valueOf(this.cValue));
                        break;
                    case "page":
                        this.cPage.postParsing();
                        this.allPages.add(this.cPage);
                        this.cMode = MODES.NONE;
                        break;
                    case "restrictions":
                        this.cPage.setRestrictions(this.cValue);
                        break;
                }
                break;
            case REVISION:
                switch (qName) {
                    case "id":
                        this.cRevision.setID(Integer.valueOf(this.cValue));
                        break;
                    case "text":
                        this.cRevision.setText(this.cValue);
                        break;
                    case "parentid":
                        this.cRevision.setParentID(Integer.valueOf(this.cValue));
                        break;
                    case "revision":
                        this.cRevision.setParentRevision(this.parentRevision);
                        // Only set the parentID if a parent exists.
                        if (parentRevision != null) {
                            this.cRevision.setParentID(this.parentRevision.getParentID());
                        }
                        this.cPage.getRevisions().add(this.cRevision);
                        this.cMode = MODES.PAGE;
                        this.parentRevision = this.cRevision;
                        this.cRevision.postParsing();
                        break;
                }
                break;
            case CONTRIBUTOR:
                switch (qName) {
                    case "username":
                        this.cContributor.setUsername(this.cValue);
                        break;
                    case "id":
                        this.cContributor.setID(Integer.valueOf(this.cValue));
                        break;
                    case "ip":
                        this.cContributor.setIP(this.cValue);
                        break;
                    case "contributor":
                        this.cContributor.postParsing();
                        this.cRevision.setContributor(this.cContributor);
                        this.cMode = MODES.REVISION;
                }
        }
    }

    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException {
        // Read content between tags as string.
        // This might not capture all content between a string, so take care.
        this.cValue = new String(chars, i, i1);
    }

    @Override
    public void ignorableWhitespace(char[] chars, int i, int i1) throws SAXException {

    }

    @Override
    public void processingInstruction(String s, String s1) throws SAXException {

    }

    @Override
    public void skippedEntity(String s) throws SAXException {

    }

    /**
     * Getter for the list of all parsed pages.
     *
     * @return the list of all parsed pages.
     */
    public ArrayList<IPage<IRevision, IPageview>> getAllPages() {
        return allPages;
    }

    /**
     * Setter for the list of all parsed pages. This should usually not be used.
     *
     * @param allPages the new list of parsed pages.
     */
    public void setAllPages(ArrayList<IPage<IRevision, IPageview>> allPages) {
        this.allPages = allPages;
    }

    /**
     * Switching modes for inner tag parsing.
     * NONE: We're not parsing a specific tag.
     * PAGE: We're insinde a <page>-tag
     * REVISION: We're inside a <revision>-tag
     * CONTRIBUTOR: We're inside a <contributor>-tag.
     */
    private enum MODES {
        NONE, PAGE, REVISION, CONTRIBUTOR
    }
}

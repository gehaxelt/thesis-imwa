package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.impl.data.Contributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.*;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Class for parsing wikipedia XML data and making sure that consecutive revisions by the same
 * author are handled correctly (e.g. only the latest revision is kept).
 * <p>
 * Created by gehaxelt on 17.01.16.
 */
public class SkipXMLContentHandler implements ContentHandler {


    public static final String invalidUsername = "##<<__-=ANONYMOUS=-__>>##";

    /**
     * The currently processed value.
     */
    String cValue;
    /**
     * List of all parsed pages.
     */
    private ArrayList<IPage<IRevision, IPageview>> allPages = new ArrayList<>();
    /**
     * Value between the current tag.
     */
    private StringBuilder cValueBuilder;
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
     * The relative ID of a revision within the page.
     */
    private int withInPageID;

    /**
     * Current parsing mode.
     */
    private MODES cMode;

    /**
     * The DumpParserConfig containing the necessary factory instances.
     */
    private DumpParserConfig config;

    private boolean skipPage;

    /**
     * Constructor which sets the config and initializes the cValueBuilder.
     *
     * @param config the config containing factory instances.
     */
    public SkipXMLContentHandler(DumpParserConfig config) {
        this.config = config;
        this.cValueBuilder = new StringBuilder();
        this.skipPage = false;
    }

    /**
     * Parses XML Dump from wikipedia. We're only interested in <page>...<revision>...<contributor></contributor></revision></page>
     *
     * @param filePath - Path to file to read XML data from.
     * @return SkipXMLContentHandler with parsed data.
     */
    public static SkipXMLContentHandler parseXML(String filePath, DumpParserConfig config) {
        // Instantiate the SkipXMLContentHandler
        SkipXMLContentHandler xmlHandler = new SkipXMLContentHandler(config);
        try {
            // Setup everything so that we can read the data from the file.
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            FileReader fReader = new FileReader(filePath);
            InputSource iSource = new InputSource(fReader);

            // Set the handler and start the parsing process.
            xmlReader.setContentHandler(xmlHandler);
            xmlReader.parse(iSource);
            fReader.close();
            return xmlHandler;

        } catch (SAXException | IOException e) {
            return null;
        }

    }

    /**
     * Parses an wikipedia XML dump string with the SkipXMLContentHandler.
     *
     * @param pageXML the XML data to parse.
     * @param config  the config with the object factories.
     * @return the content handler with the parsed Data.
     */
    public static ArrayList<IPage<IRevision, IPageview>> parseXMLString(String pageXML, DumpParserConfig config) {
        // Instantiate the SkipXMLContentHandler
        SkipXMLContentHandler xmlHandler = new SkipXMLContentHandler(config);
        try {
            // Setup a StringReader to read the stream.
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            InputSource iSource = new InputSource(new StringReader(pageXML));

            // Use the SkipXMLContentHandler to parse the data.
            xmlReader.setContentHandler(xmlHandler);
            xmlReader.parse(iSource);

            return xmlHandler.getAllPages();
        } catch (IOException | SAXException e) {
            return null;
        }
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
        // Do not look at elements from skipped pages.
        if (this.skipPage && ! qName.equals("page")) {
            return;
        }
        // Depending on the starting tag, we parse different nodes.
        switch (qName) {
            case "page":
                this.cPage = this.config.getPageFactory().newPage();
                this.cMode = MODES.PAGE;
                // Reset the parentRevision and withInPageID.
                this.parentRevision = null; //The first revision does not have a parent
                this.withInPageID = 0;
                this.skipPage = false;
                break;
            case "revision":
                this.cRevision = this.config.getRevisionFactory().newRevision();
                this.cRevision.setPage(this.cPage);
                this.cMode = MODES.REVISION;
                this.withInPageID++; // The first revision has ID 1, the following ones are counted upwards.
                break;
            case "contributor":
                this.cContributor = this.config.getContributorFactory().newContributor();
                this.cMode = MODES.CONTRIBUTOR;
                break;
            case "redirect":
                // Pages with a <redirect> tag and a namespaced title (indicated by ":") are skipped by Adler et al.
                if(this.cMode == MODES.PAGE && this.cPage.getTitle().contains(":")) {
                    this.skipPage = true;
                }
                break;
            default:
                // We don't care about other starting tags. Just let them pass.
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // After an ending tag has been found, parse and assign the value to a given object.
        // Convert the last value between tags to a string.
        this.cValue = this.cValueBuilder.toString().trim();
        // Reset the cValueBuilder, so that we can parse new attributes.
        this.cValueBuilder.delete(0, this.cValueBuilder.length());

        //Do not further process pages which should be skipped.
        if (this.skipPage) {
            return;
        }

        switch (this.cMode) {
            // We're parsing tags within a page.
            case PAGE:
                switch (qName) {
                    case "id":
                        this.cPage.setID(Integer.valueOf(cValue.trim()));
                        break;
                    case "title":
                        this.cPage.setTitle(cValue);
                        break;
                    case "ns":
                        this.cPage.setNameSpace(Integer.valueOf(cValue.trim()));
                        break;
                    case "page":
                        this.cPage.postParsing();
                        this.allPages.add(this.cPage);
                        this.cMode = MODES.NONE;
                        break;
                    case "restrictions":
                        this.cPage.setRestrictions(cValue);
                        break;
                }
                break;
            // We're parsing tags within a revision
            case REVISION:
                switch (qName) {
                    case "id":
                        this.cRevision.setID(Integer.valueOf(cValue.trim()));
                        break;
                    case "text":
                        this.cRevision.setText(cValue);
                        break;
                    case "parentid":
                        this.cRevision.setParentID(Integer.valueOf(cValue.trim()));
                        break;
                    case "revision":
                        // We're at the end of a revision. Set the mode to PAGE.
                        // If this was not the last revision, the startElement-switch case will
                        // set the REVISION mode again.
                        this.cMode = MODES.PAGE;
                        this.cRevision.setWithInPageID(this.withInPageID);

                        // If there is a parent revision, we have to check if this is a consecutive
                        // revision by the same author and remove the previous one.
                        if (parentRevision != null ) {
                            boolean skipRevision = false;
                            if (
                                // deleted="deleted" revisions without a contributor can follow each other. Skip them as normal.
                                    (parentRevision.getContributor().getUsername() == null && null == this.cRevision.getContributor().getUsername() && parentRevision.getContributor().getIP() == null && null == this.cRevision.getContributor().getIP())
                                            || // The contributor ids are the same (sometimes with different usernames) and not the default value.
                                            (parentRevision.getContributor().getID() == this.cRevision.getContributor().getID() && this.cRevision.getContributor().getID() != -1)
                                            || // The username is not null and matches the parent revision.
                                            (parentRevision.getContributor().getUsername() != null && !parentRevision.getContributor().getUsername().equals(SkipXMLContentHandler.invalidUsername) && parentRevision.getContributor().getUsername().equals(this.cRevision.getContributor().getUsername()))
                                            || // The IP is not null and matches the parent revision.
                                            (parentRevision.getContributor().getIP() != null && parentRevision.getContributor().getIP().equals(this.cRevision.getContributor().getIP()))
                                    ) {
                                skipRevision = true;
                            }
                            if (skipRevision) {
                                // Our revision list should look like this: [v_-2]->[v_-1]->[current]
                                // The authors of [v_-1] and [current] are the same, so update the list to be
                                // [v_-2]->[current]
                                IRevision oldParentRevision = this.parentRevision;
                                IRevision newParentRevision = this.parentRevision.getParentRevision();

                                this.cPage.getRevisions().remove(oldParentRevision);
                                this.parentRevision = newParentRevision;

                            }
                            // If there is a parent, update its childRevision value.
                            if (this.parentRevision != null) {
                                this.cRevision.setParentID(this.parentRevision.getParentID());
                                this.parentRevision.setChildRevision(this.cRevision);
                            }
                        }
                        // If the parent is null, this is probably the first revision.
                        this.cRevision.setParentRevision(this.parentRevision);
                        // Add this revision to the output list.
                        this.cRevision.postParsing();
                        this.cPage.getRevisions().add(this.cRevision);
                        // Now the currently parsed revision becomes the parent revision for the next one.
                        this.parentRevision = this.cRevision;
                        break;
                }
                break;
            case CONTRIBUTOR:
                switch (qName) {
                    case "username":
                        this.cContributor.setUsername(cValue);
                        break;
                    case "id":
                        this.cContributor.setID(Integer.valueOf(cValue.trim()));
                        break;
                    case "ip":
                        this.cContributor.setIP(cValue);
                        this.cContributor.setUsername(SkipXMLContentHandler.invalidUsername);
                        break;
                    case "contributor":
                        // Once we finished parsing a contributor, set it to be the current's revision author.
                        this.cContributor.postParsing();
                        this.cRevision.setContributor(this.cContributor);
                        this.cMode = MODES.REVISION;
                        break;
                }
                break;
        }
    }

    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException {
        // Read content between tags as string.
        // We have to append, because this function might not get passed *ALL* the data at once,
        // but only a smaller chunk.
        this.cValueBuilder.append(chars, i, i1);
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

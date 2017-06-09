package it.neef.tub.dima.ba.imwa.impl.parser;

import it.neef.tub.dima.ba.imwa.interfaces.data.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class RegexXMLPageParser  {


    public static final String invalidUsername = "##<<__-=ANONYMOUS=-__>>##";
    private static final Pattern tagPageStart = Pattern.compile("<page>");
    private static final Pattern tagPageEnd = Pattern.compile("</page>");
    private static final Pattern tagRevisionStart = Pattern.compile("<revision>");
    private static final Pattern tagRevisionEnd = Pattern.compile("</revision>");
    private static final Pattern tagContributorStart = Pattern.compile("<contributor>");
    private static final Pattern tagContributorEnd = Pattern.compile("</contributor>");
    private static final Pattern tagRedirect = Pattern.compile("<redirect title=\"(.*)\" />");
    private static final Pattern tagTextStart = Pattern.compile("<text xml:space=\"preserve.*\">"); //wikitrust does not use .* here, missing out some revisions!
    private static final Pattern tagTextEnd = Pattern.compile("</text>");
    private static final Pattern tagID = Pattern.compile("<id>(.*)</id>");
    private static final Pattern tagParentID = Pattern.compile("<parentid>(.*)</parentid>");
    private static final Pattern tagIP = Pattern.compile("<ip>(.*)</ip>");
    private static final Pattern tagTitle = Pattern.compile("<title>(.*)</title>");
    private static final Pattern tagUsername = Pattern.compile("<username>(.*)</username>");
    private static final Pattern tagNs = Pattern.compile("<ns>(.*)</ns>");

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
    public RegexXMLPageParser(DumpParserConfig config) {
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
    public static RegexXMLPageParser parseXML(String filePath, DumpParserConfig config) {
        // Instantiate the SkipXMLContentHandler
        RegexXMLPageParser xmlHandler = new RegexXMLPageParser(config);
        try {
            // Setup everything so that we can read the data from the file.
            xmlHandler.parseInput(new BufferedReader(new FileReader(filePath)));
            return xmlHandler;

        } catch (Exception e) {
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
        RegexXMLPageParser xmlHandler = new RegexXMLPageParser(config);
        try {
            // Setup a StringReader to read the stream
            xmlHandler.parseInput(new BufferedReader(new StringReader(pageXML)));
            return xmlHandler.getAllPages();
        } catch (Exception  e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println("Error: " + e);
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error: " + e.getLocalizedMessage());
            System.out.println("Error: " + sw.toString());
            return null;
        }
    }

    private void parseInput(BufferedReader bufferedReader) throws IOException{
        String readLine;
        this.cMode = MODES.NONE;
        while((readLine = bufferedReader.readLine()) != null) {

            if(this.skipPage && ! hasTag(readLine, tagPageStart)) {
                continue;
            } else {
              this.skipPage = false;
            }
            System.out.println("READ LINE:" + readLine);

            // Check if a new tag starts and initialize the correct section and set the correct mode.
            if(hasTag(readLine, tagPageStart)) {
                //System.out.println("#Pagestart:" + readLine);
                // A new page starts
                this.cPage = this.config.getPageFactory().newPage();
                this.cMode = MODES.PAGE;
                // Reset the parentRevision and withInPageID.
                this.parentRevision = null; //The first revision does not have a parent
                this.withInPageID = 0;
                this.skipPage = false;
                continue;
            } else if (hasTag(readLine, tagRevisionStart)) {
                //System.out.println("Revisionstart:" + readLine);
                // A new revision starts
                this.cRevision = this.config.getRevisionFactory().newRevision();
                this.cRevision.setPage(this.cPage);
                this.cMode = MODES.REVISION;
                this.withInPageID++; // The first revision has ID 1, the following ones are counted upwards.

                // We don't want 'null' contributors, so initialize them here.
                this.cContributor = this.config.getContributorFactory().newContributor();
                this.cRevision.setContributor(this.cContributor);
                // Same goes for the text, which is empty by default
                this.cRevision.setText("");
                continue;
            } else if(hasTag(readLine, tagContributorStart)) {
                //System.out.println("Contributorstart:" + readLine);
                // A new contributor starts
                this.cMode = MODES.CONTRIBUTOR;
                continue;
            }


            if(this.cMode == MODES.CONTRIBUTOR) {
                // We're in contributor mode
                if(hasTag(readLine, tagContributorEnd)) {
                    this.cContributor.postParsing();
                    this.cRevision.setContributor(this.cContributor);
                    this.cMode = MODES.REVISION;
                    //System.out.println("#Finalcontributor:" );
                    continue;
                } else if (hasTag(readLine, tagID)) {
                    this.cContributor.setID(Integer.valueOf(getMatch(readLine, tagID)));
                    continue;
                } else if(hasTag(readLine, tagIP)) {
                    this.cContributor.setIP(getMatch(readLine, tagIP));
                    this.cContributor.setUsername(RegexXMLPageParser.invalidUsername);
                    continue;
                } else if(hasTag(readLine, tagUsername)) {
                    this.cContributor.setUsername(getMatch(readLine, tagUsername));
                    continue;
                }
            } else if (this.cMode == MODES.REVISION) {
                if(hasTag(readLine, tagRevisionEnd)) {
                    // We're at the end of a revision. Set the mode to PAGE.
                    // If this was not the last revision, the startElement-switch case will
                    // set the REVISION mode again.
                    this.cMode = MODES.PAGE;
                    this.cRevision.setWithInPageID(this.withInPageID);

                    // If there is a parent revision, we have to check if this is a consecutive
                    // revision by the same author and remove the previous one.
                    if (parentRevision != null ) {
                        boolean skipRevision = false;
                        System.out.println("PARENT: " + this.parentRevision);
                        System.out.println("CURRENT: " + this.cRevision);
                        if (
                            // deleted="deleted" revisions without a contributor can follow each other. Skip them as normal.
                                (parentRevision.getContributor().getUsername() == null && null == this.cRevision.getContributor().getUsername() && parentRevision.getContributor().getIP() == null && null == this.cRevision.getContributor().getIP())
                                        || // The contributor ids are the same (sometimes with different usernames) and not the default value.
                                        (parentRevision.getContributor().getID() == this.cRevision.getContributor().getID() && this.cRevision.getContributor().getID() != -1)
                                        || // The username is not null and matches the parent revision.
                                        (parentRevision.getContributor().getUsername() != null && !parentRevision.getContributor().getUsername().equals(RegexXMLPageParser.invalidUsername) && parentRevision.getContributor().getUsername().equals(this.cRevision.getContributor().getUsername()))
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
                    //System.out.println("#Finalrevision:");
                    continue;
                } else if (hasTag(readLine, tagID)) {
                    this.cRevision.setID(Integer.valueOf(getMatch(readLine, tagID)));
                    continue;
                }else if (hasTag(readLine, tagParentID)) {
                    this.cRevision.setParentID(Integer.valueOf(getMatch(readLine, tagParentID)));
                    continue;
                } else if(hasTag(readLine, tagTextStart)) {
                    this.cMode = MODES.TEXT;
                    this.cValueBuilder.delete(0, this.cValueBuilder.length());
                    readLine = readLine.replace(readLine.substring(0, readLine.indexOf(">")+">".length()),"");
                    if(! hasTag(readLine, tagTextEnd)) {
                        this.cValueBuilder.append(readLine);
                        this.cValueBuilder.append("\n");
                        continue;
                    }
                    //System.out.println("Textstart");
                }
            } else if(this.cMode == MODES.PAGE) {
                if(hasTag(readLine, tagPageEnd)) {
                    this.cPage.postParsing();
                    this.allPages.add(this.cPage);
                    this.cMode = MODES.NONE;
                    //System.out.println("#Finalpage:");
                    continue;
                } else if (hasTag(readLine, tagID)) {
                    this.cPage.setID(Integer.valueOf(getMatch(readLine, tagID)));
                    continue;
                } else if(hasTag(readLine, tagTitle)) {
                    this.cPage.setTitle(getMatch(readLine, tagTitle));
                    continue;
                } else if(hasTag(readLine, tagNs)) {
                    this.cPage.setNameSpace(Integer.valueOf(getMatch(readLine, tagNs)));
                    continue;
                } else if(hasTag(readLine, tagRedirect)) {
                    if(this.cPage.getTitle().contains(":")) {
                       this.skipPage = true;
                        continue;
                    }
                }
            }

           if(this.cMode == MODES.TEXT) {
               if (hasTag(readLine, tagTextEnd)) {
                   this.cValueBuilder.append(readLine.replace("</text>",""));
                   this.cRevision.setText(StringEscapeUtils.unescapeXml(this.cValueBuilder.toString().trim()));
                   this.cMode = MODES.REVISION;
                   //System.out.println("#Finaltext: blall");
                   continue;
               } else {
                   this.cValueBuilder.append(readLine);
                   this.cValueBuilder.append("\n");
                   continue;
               }
           }
        }
    }

    private boolean hasTag(String line, Pattern regex) {
        try {
            return regex.matcher(line).find();
        } catch(Exception e) {
            System.out.println("Error for'" + line + "' with " + regex + ": " + e);
            return false;
        }
    }

    private String getMatch(String line, Pattern regex) {
        try {
            Matcher m = regex.matcher(line).reset();
            m.find();
            return m.group(1);
        }catch(Exception e) {
            System.out.println("Error for'" + line + "' with " + regex + ": " + e);
            return "NO MATCH!";
        }
    }

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
                                            (parentRevision.getContributor().getUsername() != null && !parentRevision.getContributor().getUsername().equals(RegexXMLPageParser.invalidUsername) && parentRevision.getContributor().getUsername().equals(this.cRevision.getContributor().getUsername()))
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
                        this.cContributor.setUsername(RegexXMLPageParser.invalidUsername);
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
        NONE, PAGE, REVISION, CONTRIBUTOR, TEXT
    }
}

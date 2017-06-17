package it.neef.tub.dima.ba.imwa.tests.parser;

import it.neef.tub.dima.ba.imwa.Framework;
import it.neef.tub.dima.ba.imwa.impl.parser.DumpParserConfig;
import it.neef.tub.dima.ba.imwa.impl.parser.SkipXMLContentHandler;
import it.neef.tub.dima.ba.imwa.interfaces.data.IContributor;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPage;
import it.neef.tub.dima.ba.imwa.interfaces.data.IPageview;
import it.neef.tub.dima.ba.imwa.interfaces.data.IRevision;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 15.06.17.
 */
public class SkipXMLContentHandlerTest {

    ArrayList<IPage> pageList;
    ArrayList<IPage<IRevision, IPageview>> parsedPages;
    ArrayList<IRevision> p1revs, p2revs, p3revs;
    DumpParserConfig dumpParserConfig;
    String wikiXML;
    IPage page1, page2, page3;
    IRevision rev1, rev2, rev3, rev4, rev5, rev6, rev7, rev8, rev9;
    IContributor con1, con2, con3, con4;


    @Before
    public void setUp() throws Exception {
        dumpParserConfig = new DumpParserConfig(Framework.getInstance().getConfiguration().getPageFactory(), Framework.getInstance().getConfiguration().getRevisionFactory(), Framework.getInstance().getConfiguration().getContributorFactory());

        con1 = Framework.getInstance().getConfiguration().getContributorFactory().newContributor();
        con2 = Framework.getInstance().getConfiguration().getContributorFactory().newContributor();
        con3 = Framework.getInstance().getConfiguration().getContributorFactory().newContributor();
        con4 = Framework.getInstance().getConfiguration().getContributorFactory().newContributor();

        rev1 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev2 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev3 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev4 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev5 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev6 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev7 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev8 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();
        rev9 = Framework.getInstance().getConfiguration().getRevisionFactory().newRevision();

        pageList = new ArrayList<>();
        page1 = Framework.getInstance().getConfiguration().getPageFactory().newPage();
        page2 = Framework.getInstance().getConfiguration().getPageFactory().newPage();
        page3 = Framework.getInstance().getConfiguration().getPageFactory().newPage();

        p1revs = new ArrayList<>();
        p2revs = new ArrayList<>();
        p3revs = new ArrayList<>();

        con1.setUsername("User1");
        con1.setID(1);

        con2.setUsername("User2");
        con2.setID(2);

        con3.setUsername("User3");
        con3.setID(3);

        con4.setUsername("User4");
        con4.setID(4);

        rev1.setID(1);
        rev1.setPage(page1);
        rev1.setContributor(con1);
        rev1.setText("First");
        rev1.setParentRevision(null);
        rev1.setChildRevision(rev4);
        rev1.setWithInPageID(1);
        p1revs.add(rev1);

        rev2.setID(2);
        rev2.setPage(page1);
        rev2.setContributor(con2);
        rev2.setText("First Second");
        rev2.setWithInPageID(2);

        rev3.setID(3);
        rev3.setPage(page1);
        rev3.setContributor(con2);
        rev3.setText("First Second Third");
        rev3.setWithInPageID(3);

        rev4.setID(4);
        rev4.setPage(page1);
        rev4.setContributor(con2);
        rev4.setText("First Second");
        rev4.setWithInPageID(4);
        rev4.setParentRevision(rev1);
        rev4.setChildRevision(rev5);
        p1revs.add(rev4);

        rev5.setID(5);
        rev5.setPage(page1);
        rev5.setContributor(con3);
        rev5.setText("Third First Test Second");
        rev5.setWithInPageID(5);
        rev5.setParentRevision(rev4);
        rev5.setChildRevision(rev5);
        p1revs.add(rev5);

        rev6.setID(6);
        rev6.setPage(page2);
        rev6.setContributor(con1);
        rev6.setText("Hello world");
        rev6.setWithInPageID(1);

        rev7.setID(7);
        rev7.setPage(page2);
        rev7.setContributor(con1);
        rev7.setText("Hello wiki");
        rev7.setWithInPageID(2);
        rev7.setParentRevision(null);
        rev7.setChildRevision(rev8);
        p2revs.add(rev7);

        rev8.setID(8);
        rev8.setPage(page2);
        rev8.setContributor(con4);
        rev8.setText("Hello wiki world");
        rev8.setWithInPageID(3);
        rev8.setParentRevision(rev7);
        rev8.setChildRevision(null);
        p2revs.add(rev8);

        rev9.setID(9);
        rev9.setPage(page3);
        rev9.setContributor(con3);
        rev9.setText("Other namespace");
        rev9.setWithInPageID(1);
        rev9.setParentRevision(null);
        rev9.setChildRevision(null);
        p3revs.add(rev9);


        page1.setTitle("Page1");
        page1.setNameSpace(0);
        page1.setID(1);
        page1.setRevisions(p1revs);

        page2.setTitle("Page2");
        page2.setNameSpace(0);
        page2.setID(2);
        page2.setRevisions(p2revs);

        page3.setTitle("Page3");
        page3.setNameSpace(1);
        page3.setID(3);
        page3.setRevisions(p3revs);

        pageList.add(page1);
        pageList.add(page2);
        pageList.add(page3);

        wikiXML = "<mediawiki xmlns=\"http://www.mediawiki.org/xml/export-0.10/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.mediawiki.org/xml/export-0.10/ http://www.mediawiki.org/xml/export-0.10.xsd\" version=\"0.10\" xml:lang=\"aa\">\n" +
                "  <siteinfo>\n" +
                "    <sitename>Testipedia</sitename>\n" +
                "    <dbname>testwiki</dbname>\n" +
                "    <base>https://testipedia.com</base>\n" +
                "    <generator>MediaWiki 1.27.0-wmf.9</generator>\n" +
                "    <case>first-letter</case>\n" +
                "    <namespaces>\n" +
                "      <namespace key=\"0\" case=\"first-letter\" />\n" +
                "      <namespace key=\"1\" case=\"first-letter\">Topic</namespace>\n" +
                "    </namespaces>\n" +
                "  </siteinfo>\n" +
                "  <page>\n" +
                "    <title>Page1</title>\n" +
                "    <ns>0</ns>\n" +
                "    <id>1</id>\n" +
                "    <restrictions>edit=autoconfirmed:move=autoconfirmed</restrictions>\n" +
                "    <revision>\n" +
                "      <id>1</id>\n" +
                "      <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User1</username>\n" +
                "        <id>1</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">First</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "    <revision>\n" +
                "      <id>2</id>\n" +
                "      <timestamp>2005-07-07T15:31:38Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User2</username>\n" +
                "        <id>2</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">First Second</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "    <revision>\n" +
                "      <id>3</id>\n" +
                "      <timestamp>2005-07-07T15:31:39Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User2</username>\n" +
                "        <id>2</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">First Second Third</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "    <revision>\n" +
                "      <id>4</id>\n" +
                "      <timestamp>2005-07-07T15:31:40Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User2</username>\n" +
                "        <id>2</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">First Second</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "    <revision>\n" +
                "      <id>5</id>\n" +
                "      <timestamp>2006-07-07T15:31:40Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User3</username>\n" +
                "        <id>3</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">Third First Test Second</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "  </page>\n" +
                "\n" +
                "  <page>\n" +
                "    <title>Page2</title>\n" +
                "    <ns>0</ns>\n" +
                "    <id>2</id>\n" +
                "    <restrictions>edit=autoconfirmed:move=autoconfirmed</restrictions>\n" +
                "    <revision>\n" +
                "      <id>6</id>\n" +
                "      <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User1</username>\n" +
                "        <id>1</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">Hello world</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "    <revision>\n" +
                "      <id>7</id>\n" +
                "      <timestamp>2005-07-07T15:31:38Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User1</username>\n" +
                "        <id>1</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">Hello wiki</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "    <revision>\n" +
                "      <id>8</id>\n" +
                "      <timestamp>2005-07-07T15:31:39Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User4</username>\n" +
                "        <id>4</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">Hello wiki world</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "  </page>\n" +
                "\n" +
                "  <page>\n" +
                "    <title>Page3</title>\n" +
                "    <ns>1</ns>\n" +
                "    <id>3</id>\n" +
                "    <restrictions>edit=autoconfirmed:move=autoconfirmed</restrictions>\n" +
                "    <revision>\n" +
                "      <id>9</id>\n" +
                "      <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "      <contributor>\n" +
                "        <username>User3</username>\n" +
                "        <id>3</id>\n" +
                "      </contributor>\n" +
                "      <model>wikitext</model>\n" +
                "      <format>text/x-wiki</format>\n" +
                "      <text xml:space=\"preserve\">Other namespace</text>\n" +
                "      <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "    </revision>\n" +
                "  </page>\n" +
                "</mediawiki>";

        parsedPages = SkipXMLContentHandler.parseXMLString(wikiXML, dumpParserConfig);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseXMLString() throws Exception {
        //There is no error.
        assertNotNull(parsedPages);
        //We parsed all three pages
        assertEquals(parsedPages.size(), 3);
    }

    @Test
    public void parseXMLStringPages() throws Exception {
        IPage rp1, rp2, rp3;
        rp1 = parsedPages.get(0);
        rp2 = parsedPages.get(1);
        rp3 = parsedPages.get(2);

        //Check IDs
        assertEquals(page1.getID(), rp1.getID());
        assertEquals(page2.getID(), rp2.getID());
        assertEquals(page3.getID(), rp3.getID());

        //Check namespace
        assertEquals(page1.getNameSpace(), rp1.getNameSpace());
        assertEquals(page2.getNameSpace(), rp2.getNameSpace());
        assertEquals(page3.getNameSpace(), rp3.getNameSpace());

        //Check title
        assertEquals(page1.getTitle(), rp1.getTitle());
        assertEquals(page2.getTitle(), rp2.getTitle());
        assertEquals(page3.getTitle(), rp3.getTitle());

        //Check Revision list length
        assertEquals(page1.getRevisions().size(), rp1.getRevisions().size());
        assertEquals(page2.getRevisions().size(), rp2.getRevisions().size());
        assertEquals(page3.getRevisions().size(), rp3.getRevisions().size());
    }

    @Test
    public void parseXMLStringRevisionsAndContributors() throws Exception {
        IRevision expectedRev, parsedRev;
        IContributor expectedCon, parsedCon;

        for(int k = 0; k < parsedPages.size(); k++) {
            List<IRevision> parsedRevs = parsedPages.get(k).getRevisions();
            List<IRevision> expectedRevs = pageList.get(k).getRevisions();

            for (int i = 0; i < expectedRevs.size(); i++) {
                expectedRev = expectedRevs.get(i);
                parsedRev = parsedRevs.get(i);

                //Check basic values
                assertEquals(expectedRev.getID(), parsedRev.getID());
                assertEquals(expectedRev.getWithInPageID(), parsedRev.getWithInPageID());
//                assertTrue(expectedRev.getText().equals(parsedRev.getText()));
                assertEquals(expectedRev.getText(), parsedRev.getText());
                assertEquals(expectedRev.getDifference(), parsedRev.getDifference());

                // Check parent/child revisions
                if((expectedRev.getParentRevision() == null && parsedRev.getParentRevision() != null)
                    ||
                    (expectedRev.getParentRevision() != null && parsedRev.getParentRevision() == null)
                ) {
                    fail("Expected parentRevision " + expectedRev.getParentRevision() + " is null, but parsed is not!" + parsedRev.getParentRevision());
                } else if(expectedRev.getParentRevision() != null && parsedRev.getParentRevision() != null){
                    assertEquals(expectedRev.getParentRevision().getIdentifier(), parsedRev.getParentRevision().getIdentifier());
                }
                if( (expectedRev.getChildRevision() == null && parsedRev.getChildRevision() != null)
                    ||
                        (expectedRev.getChildRevision() != null && expectedRev.getChildRevision() == null)
                ) {
                    fail("Expected childRevision " + expectedRev.getChildRevision() + " is null, but parsed is not!" + parsedRev.getChildRevision());
                } else if(expectedRev.getChildRevision() != null && parsedRev.getChildRevision() != null){
                    assertEquals(expectedRev.getChildRevision().getIdentifier(), parsedRev.getChildRevision().getIdentifier());
                }
                //Check the contributors
                expectedCon = expectedRev.getContributor();
                parsedCon = parsedRev.getContributor();
                assertEquals(expectedCon.getID(), parsedCon.getID());
                assertEquals(expectedCon.getIP(), parsedCon.getIP());
                assertEquals(expectedCon.getUsername(), parsedCon.getUsername());
            }
        }
    }

    @Test
    public void edgeCase1() throws Exception {
        // When the user-ids are the same, but username are not, then WikiTrust skips the first one.
        String xml = "<page>\n" +
                "  <title>Page3</title>\n" +
                "  <ns>1</ns>\n" +
                "  <restrictions>edit=autoconfirmed:move=autoconfirmed</restrictions>\n" +
                "  <revision>\n" +
                "    <id>10</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor>\n" +
                "      <username>UserX</username>\n" +
                "      <id>0</id>\n" +
                "    </contributor>\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text xml:space=\"preserve\">Other text</text>\n" +
                "    <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "  </revision>\n" +
                "  <revision>\n" +
                "    <id>11</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor>\n" +
                "      <username>UserY</username>\n" +
                "      <id>0</id>\n" +
                "    </contributor>\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text xml:space=\"preserve\">Other namespace</text>\n" +
                "    <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "  </revision>\n" +
                "</page>";
        ArrayList<IPage<IRevision, IPageview>> pages = SkipXMLContentHandler.parseXMLString(xml, dumpParserConfig);
        assertEquals(1, pages.size());
        assertNotNull(pages.get(0));

        List<IRevision> revisions = pages.get(0).getRevisions();
        assertEquals(1, revisions.size());
        assertNotNull(revisions.get(0));

        IRevision rev = revisions.get(0);

        assertEquals(11, rev.getID());
        assertEquals("Other namespace", rev.getText());
        assertNull(rev.getChildRevision());
        assertNull(rev.getParentRevision());

        IContributor con = rev.getContributor();

        assertEquals(0, con.getID());
        assertEquals("UserY", con.getUsername());
    }

    @Test
    public void edgeCase2() throws Exception{
        // Test handling of deleted contributors and text.
        String xml = "<page>\n" +
                "  <title>Page4</title>\n" +
                "  <ns>2</ns>\n" +
                "  <restrictions>edit=autoconfirmed:move=autoconfirmed</restrictions>\n" +
                "  <revision>\n" +
                "    <id>11</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor deleted=\"deleted\" />\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text deleted=\"deleted\" />\n" +
                "    <sha1 />\n" +
                "  </revision>\n" +
                "  <revision>\n" +
                "    <id>12</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor deleted=\"deleted\" />\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text deleted=\"deleted\" />\n" +
                "    <sha1 />\n" +
                "  </revision>\n" +
                "  <revision>\n" +
                "    <id>13</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor>\n" +
                "      <username>User1337</username>\n" +
                "      <id>1337</id>\n" +
                "    </contributor>\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text xml:space=\"preserve\">Other namespace</text>\n" +
                "    <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "  </revision>\n" +
                "</page>";

        ArrayList<IPage<IRevision, IPageview>> pages = SkipXMLContentHandler.parseXMLString(xml, dumpParserConfig);
        assertEquals(1, pages.size());
        assertNotNull(pages.get(0));

        List<IRevision> revisions = pages.get(0).getRevisions();
        assertEquals(2, revisions.size());
        assertNotNull(revisions.get(0));
        assertNotNull(revisions.get(1));

        IRevision rev1  = revisions.get(0);
        IRevision rev2  = revisions.get(1);

        assertEquals(12, rev1.getID());
        assertNotNull(rev1.getText());
        assertEquals("", rev1.getText());
        assertNotNull(rev1.getContributor());
        assertNull(rev1.getContributor().getUsername());
        assertNull(rev1.getContributor().getIP());
        assertNull(rev1.getParentRevision());
        assertEquals(rev2, rev1.getChildRevision());

        assertEquals(13, rev2.getID());
        assertEquals("Other namespace", rev2.getText());
        assertEquals(rev1.getID(), rev2.getParentRevision().getID());
        assertNull(rev2.getChildRevision());

    }

    @Test
    public void edgeCase3() throws Exception{
        // Test handling and skipping of deleted contributors and text.
        String xml = "<page>\n" +
                "  <title>Page4</title>\n" +
                "  <ns>2</ns>\n" +
                "  <restrictions>edit=autoconfirmed:move=autoconfirmed</restrictions>\n" +
                "  <revision>\n" +
                "    <id>12</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor deleted=\"deleted\" />\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text deleted=\"deleted\" />\n" +
                "    <sha1 />\n" +
                "  </revision>\n" +
                "  <revision>\n" +
                "    <id>13</id>\n" +
                "    <timestamp>2005-07-07T15:31:37Z</timestamp>\n" +
                "    <contributor>\n" +
                "      <username>User1337</username>\n" +
                "      <id>1337</id>\n" +
                "    </contributor>\n" +
                "    <model>wikitext</model>\n" +
                "    <format>text/x-wiki</format>\n" +
                "    <text xml:space=\"preserve\">Other namespace</text>\n" +
                "    <sha1>sgjoro1gzkwvszypw7metsc9m886zgx</sha1>\n" +
                "  </revision>\n" +
                "</page>";

        ArrayList<IPage<IRevision, IPageview>> pages = SkipXMLContentHandler.parseXMLString(xml, dumpParserConfig);
        assertEquals(1, pages.size());
        assertNotNull(pages.get(0));

        List<IRevision> revisions = pages.get(0).getRevisions();
        assertEquals(2, revisions.size());
        assertNotNull(revisions.get(0));
        assertNotNull(revisions.get(1));

        IRevision rev1  = revisions.get(0);
        IRevision rev2  = revisions.get(1);

        assertEquals(12, rev1.getID());
        assertNotNull(rev1.getText());
        assertEquals("", rev1.getText());
        assertNotNull(rev1.getContributor());
        assertNull(rev1.getContributor().getUsername());
        assertNull(rev1.getContributor().getIP());
        assertNull(rev1.getParentRevision());
        assertEquals(rev2, rev1.getChildRevision());

        assertEquals(13, rev2.getID());
        assertEquals("Other namespace", rev2.getText());
        assertEquals(rev1.getID(), rev2.getParentRevision().getID());
        assertNull(rev2.getChildRevision());
    }

}
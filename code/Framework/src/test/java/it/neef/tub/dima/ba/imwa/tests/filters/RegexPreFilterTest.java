package it.neef.tub.dima.ba.imwa.tests.filters;

import it.neef.tub.dima.ba.imwa.impl.filters.pre.RegexPreFilter;
import it.neef.tub.dima.ba.imwa.interfaces.filters.pre.IRegexPreFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class RegexPreFilterTest {

    String wikiXML;
    IRegexPreFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new RegexPreFilter();

        wikiXML = "<page>\n" +
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
                "  </page>\n";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void filterTrue() throws Exception {
        filter.setRegex("(?is).*<ns>0</ns>.*");

        assertTrue(filter.filter(wikiXML));
    }

    @Test
    public void filterFalse() throws Exception {
        filter.setRegex("(?is).*<ns>1</ns>.*");

        assertFalse(filter.filter(wikiXML));
    }

}
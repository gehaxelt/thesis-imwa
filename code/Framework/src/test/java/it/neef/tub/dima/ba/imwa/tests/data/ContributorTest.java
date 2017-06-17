package it.neef.tub.dima.ba.imwa.tests.data;

import it.neef.tub.dima.ba.imwa.impl.data.Contributor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class ContributorTest {

    Contributor c1, c2, c3, c4;

    @Before
    public void setUp() throws Exception {
        c1 = new Contributor();
        c1.setUsername("foo");

        c2 = new Contributor();
        c2.setUsername("bar");

        c3 = new Contributor();
        c3.setIP("127.0.0.1");

        c4 = new Contributor();
        c4.setIP("10.0.0.1");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getIdentifier() throws Exception {
        assertEquals(c1.getIdentifier(), c1.getIdentifier());
        assertEquals(c2.getIdentifier(), c2.getIdentifier());
        assertEquals(c3.getIdentifier(), c3.getIdentifier());
        assertEquals(c4.getIdentifier(), c4.getIdentifier());

        assertNotSame(c1.getIdentifier(), c2.getIdentifier());
        assertNotSame(c3.getIdentifier(), c4.getIdentifier());
    }

}
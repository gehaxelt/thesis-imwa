package it.neef.tub.dima.ba.imwa.tests.calc;

import it.neef.tub.dima.ba.imwa.impl.calc.ArgumentsBundle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class ArgumentsBundleTest {

    ArgumentsBundle ab;
    String key, value;

    @Before
    public void setUp() throws Exception {
        key = "foo";
        value = "bar";
        ab = new ArgumentsBundle();
        ab.addArgument(key, value);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getArgument() throws Exception {
        assertEquals(value, ab.getArgument(key));
        assertNull(ab.getArgument("invalidKey"));
    }

    @Test
    public void argumentCount() throws Exception {
        assertSame(1, ab.argumentCount());
    }

}
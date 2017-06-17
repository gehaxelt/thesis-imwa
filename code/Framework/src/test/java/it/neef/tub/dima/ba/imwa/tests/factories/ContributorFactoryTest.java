package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.Contributor;
import it.neef.tub.dima.ba.imwa.impl.factories.data.ContributorFactory;
import it.neef.tub.dima.ba.imwa.impl.factories.data.PageFactory;
import it.neef.tub.dima.ba.imwa.impl.output.ConsoleOutput;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class ContributorFactoryTest {
    @Test
    public void newContributor() throws Exception {
        ContributorFactory cf = new ContributorFactory();
        assertEquals(Contributor.class, cf.newContributor().getClass());
    }

}
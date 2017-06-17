package it.neef.tub.dima.ba.imwa.tests.factories;

import it.neef.tub.dima.ba.imwa.impl.data.Revision;
import it.neef.tub.dima.ba.imwa.impl.factories.data.RevisionFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gehaxelt on 16.06.17.
 */
public class RevisionFactoryTest {
    @Test
    public void newRevision() throws Exception {
        RevisionFactory rf = new RevisionFactory();

        assertEquals(Revision.class, rf.newRevision().getClass());
    }

}
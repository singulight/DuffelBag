package ru.singulight.duffelbag.nodes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 12.08.16.
 */
public class IdCounterTest {

    private IdCounter idCounter = IdCounter.getInstance();

    @Test
    public void testGetNewId() throws Exception {
        assertNotNull(idCounter);
        long a = idCounter.getNewId();
        assertTrue((a>=0x400000000000000L)&&(a<=0x5ffffffffffffffL));
        long b = idCounter.getNewId();
        assertTrue((b>=0x400000000000000L)&&(b<=0x5ffffffffffffffL));
        assertNotEquals(a,b);
    }

    @Test
    public void testCheckDbId() throws Exception {
        long a = idCounter.checkDbId(1L);
        assertEquals(a,1L);
        a= idCounter.checkDbId(2L);
        assertEquals(a,2L);
        a = idCounter.checkDbId(1L);
        assertTrue((a>=0x400000000000000L)&&(a<=0x5ffffffffffffffL));
    }

    @Test
    public void testDeleteId() throws Exception {

    }
}
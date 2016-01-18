package ru.singulight.duffelbag.mqttnodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.singulight.duffelbag.mqttnodes.types.SensorType;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.01.16.
 */
public class SensorNodeTest {

    SensorNode sensorNode;

    @Before
    public void setUp() throws Exception {

        sensorNode = new SensorNode(0x100,"Out temperature","duffelbag/trmperature/0000000000000100", SensorType.OTHER,0,100);

    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void TestCreateObject() {
        assertTrue("object not create",sensorNode != null);
    }
}
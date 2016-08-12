package ru.singulight.duffelbag.nodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.singulight.duffelbag.nodes.types.NodeType;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.01.16.
 */
public class SensorNodeTest {

    SensorNode sensorNode;

    @Before
    public void setUp() throws Exception {

        sensorNode = new SensorNode(0x100, "duffelbag/temperature/0000000000000100", NodeType.TEMPERATURE);
        sensorNode.setName("Out temperature");

    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void TestCreateObject() {
        assertNotNull("object not create",sensorNode);
        assertEquals(sensorNode.getId(),0x100);
        assertEquals(sensorNode.getName(),"Out temperature");
        assertEquals(sensorNode.getMqttTopic(),"duffelbag/temperature/0000000000000100");
        assertEquals(sensorNode.isKnown(),false);
        assertEquals(sensorNode.getNodeType(), NodeType.TEMPERATURE);
        assertEquals(sensorNode.getMinValue(),0,0);
        assertEquals(sensorNode.getMaxValue(),100,0);
    }

}
package ru.singulight.duffelbag.nodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.singulight.duffelbag.nodes.types.NodeType;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.01.16.
 */
public class CreateNode {

    BaseNode baseNode;

    @Before
    public void setUp() throws Exception {

        baseNode = new BaseNode(0x100, "duffelbag/temperature/0000000000000100", NodeType.TEMPERATURE);
        baseNode.setName("Out temperature");

    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void TestCreateObject() {
        assertNotNull("object not create", baseNode);
        assertEquals(baseNode.getId(),0x100);
        assertEquals(baseNode.getName(),"Out temperature");
        assertEquals(baseNode.getMqttTopic(),"duffelbag/temperature/0000000000000100");
        assertEquals(baseNode.isKnown(),false);
        assertEquals(baseNode.getNodeType(), NodeType.TEMPERATURE);

    }

}
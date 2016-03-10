package ru.singulight.duffelbag.mqttnodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.singulight.duffelbag.mqttnodes.actions.ISensorAction;
import ru.singulight.duffelbag.mqttnodes.actions.LuaScriptSensorAction;
import ru.singulight.duffelbag.mqttnodes.actions.SetOfRulesSensorAction;
import ru.singulight.duffelbag.mqttnodes.types.NodeType;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.01.16.
 */
public class SensorNodeTest {

    SensorNode sensorNode;

    @Before
    public void setUp() throws Exception {

        sensorNode = new SensorNode(0x100, "duffelbag/temperature/0000000000000100", NodeType.OTHER);

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
        assertEquals(sensorNode.getSensorType(), NodeType.OTHER);
        assertEquals(sensorNode.getMinValue(),0,0);
        assertEquals(sensorNode.getMaxValue(),100,0);
    }

    @Test
    public void TestActions() {
        ISensorAction action1 = new LuaScriptSensorAction();
        ISensorAction action2 = new LuaScriptSensorAction();
        ISensorAction action3 = new SetOfRulesSensorAction();
        List<ISensorAction> ref = new LinkedList<>();
        ref.add(action1);
        ref.add(action2);
        ref.add(action3);
        sensorNode.addAction(action1);
        sensorNode.addAction(action2);
        sensorNode.addAction(action3);
        assertEquals(sensorNode.getSensorActions(),ref);
        ref.remove(action1);
        ref.remove(action2);
        ref.remove(action3);
        sensorNode.removeAction(action1);
        sensorNode.removeAction(action2);
        sensorNode.removeAction(action3);
        assertEquals(sensorNode.getSensorActions(),ref);
    }
}
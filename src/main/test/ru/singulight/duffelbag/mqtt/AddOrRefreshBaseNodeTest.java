package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.singulight.duffelbag.nodes.*;
import ru.singulight.duffelbag.web.websocket.SocketObjects;

import static org.junit.Assert.*;
import static ru.singulight.duffelbag.nodes.types.NodeType.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.03.16.
 */
public class AddOrRefreshBaseNodeTest {


    AllNodes allNodes = AllNodes.getInstance();
    SocketObjects socketObjects = SocketObjects.getInstance();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDetectAndParseDuffelbagNode() throws Exception {

        MqttMessage mqttMessage = new MqttMessage();
        assertNotNull(allNodes);
        mqttMessage.setPayload("1".getBytes());
        AddOrRefreshNode addOrRefreshNode = new AddOrRefreshNode("duffelbag/temperature/00000000000000f3", mqttMessage);
        assertNotNull(addOrRefreshNode);
        addOrRefreshNode.detectDuffelbagNode(true);
        BaseNode testingNode = allNodes.getNodeByTopic("duffelbag/temperature/00000000000000f3");
        assertNotNull(testingNode);
        assertEquals(testingNode.getId(), 0xf3);
        assertEquals(testingNode.getNodeType(), TEMPERATURE);
        assertEquals(testingNode.getValue(), "1");
        assertEquals(testingNode.getMqttTopic(), "duffelbag/temperature/00000000000000f3");
        BaseNode testingNode2 = allNodes.getNodeById((long) 0xf3);
        assertEquals(testingNode, testingNode2);

        addOrRefreshNode = new AddOrRefreshNode("duffelbag/temperature/00000000000000f3", mqttMessage);
        assertNotNull(addOrRefreshNode);
        assertNotNull(addOrRefreshNode.detectDuffelbagNode(true));

        /*Create other sensor with the same ID in topic*/

        addOrRefreshNode = new AddOrRefreshNode("duffelbag/water_flow/00000000000000f3", mqttMessage);
        assertNotNull(addOrRefreshNode);
        assertNull(addOrRefreshNode.detectDuffelbagNode(true));
    }

    @Test
    public void testDetectAndParseDuffelbagNodeThingV10() throws Exception {
        /**
         * Thing configuration JSON packet ver. 1.0
         * {
         *      "ver":"1.0",
         *      "id":"id string",                   --  string of hexadecimal id, will convert to long
         *      "name":"string of thing name",
         *      "location":"string of location",
         *      "nodes":[{                      -- array of actuators
         *                      "topic":"node's mqtt topic string",
         *                      "name":"nodes's name string",
         *                      "options":[
         *                          { "key":"minValue", "value":"0" },
         *                          { "key":"maxValue", "value":"100"},
         *                          ...
         *                      ]
         *
         *                  },{
         *                      ...
         *                      next node
         *                      ...
         *                  }]
         * }
         * */

        String testJSON = "{\"ver\":\"1.0\",\"id\":\"00000000000000f5\",\"name\":\"RGB светильник\",\"location\":\"\",\"nodes\":[{\"topic\":\"duffelbag/voltage/00000000000000f7\",\"name\":\"Входное напряжение\",\"options\":[{\"key\":\"minValue\", \"value\":\"0\"},{\"key\":\"maxValue\", \"value\":\"100\"}]},{\"topic\":\"duffelbag/power/00000000000000f8\",\"name\":\"Потребляемая мощность\",\"options\":[{\"key\":\"minValue\",\"value\":\"0\"},{\"key\":\"maxValue\",\"value\":\"30\"}]}]}";

        /* Create and add one sensor, included in this config message, before thing parse */

        MqttMessage mqttMessage1 = new MqttMessage();
        mqttMessage1.setPayload("12".getBytes());
        AddOrRefreshNode parseSensor = new AddOrRefreshNode("duffelbag/power/00000000000000f8", mqttMessage1);
        parseSensor.detectDuffelbagNode(true);
        BaseNode sensorNode1 = allNodes.getNodeById((long) 0xf8);
        assertNotNull(sensorNode1);
        assertEquals(sensorNode1.getValue(),"12");

        /* Get THING MQTT packet */

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(testJSON.getBytes());
        AddOrRefreshNode addOrRefreshNode = new AddOrRefreshNode("duffelbag/outlet/00000000000000f5", mqttMessage);
        assertNotNull(addOrRefreshNode);
        addOrRefreshNode.detectDuffelbagNode(true);

        /* Check Thing object */

        BaseNode testThing = allNodes.getNodeById((long) 0xf5);
        assertNotNull(testThing);
        assertEquals(testThing.getVersion(),"1.0");
        assertEquals(testThing.getId(), 0xf5);
        assertEquals(testThing.getNodeType(), OUTLET);
        //assertEquals(testThing.getValue(), testJSON);
        assertEquals(testThing.getMqttTopic(), "duffelbag/outlet/00000000000000f5");
        assertEquals(testThing.getName(),"RGB светильник");


        /* Check first node */

        BaseNode firstSensor = allNodes.getNodeById((long) 0xf7);
        assertNotNull(firstSensor);
        assertEquals(firstSensor.getId(), 0xf7);
        assertEquals(firstSensor.getNodeType(),VOLTAGE);
        assertEquals(firstSensor.getName(),"Входное напряжение");
        assertEquals(firstSensor.getMqttTopic(),"duffelbag/voltage/00000000000000f7");

        assertEquals(firstSensor.isKnown(),true);
        assertEquals(firstSensor.getVersion(),"1.0");

        /* Check second sensor (already exist) */

        BaseNode secondSensor = allNodes.getNodeById((long) 0xf8);
        assertNotNull(secondSensor);
        assertEquals(secondSensor.getId(), 0xf8);
        assertEquals(secondSensor.getNodeType(),POWER);
        assertEquals(secondSensor.getName(),"Потребляемая мощность");
        assertEquals(secondSensor.getMqttTopic(),"duffelbag/power/00000000000000f8");

        assertEquals(secondSensor.getValue(),"12");
        assertEquals(secondSensor.isKnown(),true);
        assertEquals(secondSensor.getVersion(),"1.0");

        JSONObject jsonObject = socketObjects.nodeToJSON(allNodes.getNodeById((long) 0xf7));
        System.out.println(jsonObject.toJSONString());
 }
}
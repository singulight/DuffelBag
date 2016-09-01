package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.singulight.duffelbag.nodes.*;
import ru.singulight.duffelbag.nodes.ActuatorNode;
import ru.singulight.duffelbag.nodes.SensorNode;

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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDetectAndParseDuffelbagNodeSensorFloat() throws Exception {

        MqttMessage mqttMessage = new MqttMessage();
        assertNotNull(allNodes);
        mqttMessage.setPayload(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(10.2f).array());
        AddOrRefreshNode addOrRefreshNode = new AddOrRefreshNode("duffelbag/temperature/00000000000000f3", mqttMessage);
        assertNotNull(addOrRefreshNode);
        addOrRefreshNode.detectDuffelbagNode();
        SensorNode testingSensor = allNodes.getSensor("duffelbag/temperature/00000000000000f3");
        assertNotNull(testingSensor);
        assertEquals(testingSensor.getId(), 0xf3);
        assertEquals(testingSensor.getNodeType(), TEMPERATURE);
        assertEquals(testingSensor.getValue(), 10.2f, 0);
        assertEquals(testingSensor.getMqttTopic(), "duffelbag/temperature/00000000000000f3");

        /*Create other sensor with the same ID in topic*/

        addOrRefreshNode = new AddOrRefreshNode("duffelbag/water_flow/00000000000000f3", mqttMessage);
        assertNotNull(addOrRefreshNode);
        addOrRefreshNode.detectDuffelbagNode();
        testingSensor = allNodes.getSensor("duffelbag/water_flow/00000000000000f3");
        assertNotNull(testingSensor);
        long a = testingSensor.getId();
        assertTrue((a>=0x400000000000000L)&&(a<=0x5ffffffffffffffL));
        assertEquals(testingSensor.getNodeType(), WATER_FLOW);
        assertEquals(testingSensor.getValue(), 10.2f, 0);
        assertEquals(testingSensor.getMqttTopic(), "duffelbag/water_flow/00000000000000f3");

    }

    @Test
    public void testDetectAndParseDuffelbagNodeSensorText() throws Exception {

        assertNotNull(allNodes);
        MqttMessage mqttMessage = new MqttMessage();

        mqttMessage.setPayload("Test message".getBytes(StandardCharsets.UTF_8));

        AddOrRefreshNode addOrRefreshNode = new AddOrRefreshNode("duffelbag/text/00000000000000f4", mqttMessage);
        assertNotNull("parser did not create", addOrRefreshNode);

        addOrRefreshNode.detectDuffelbagNode();

        SensorNode testingSensor = allNodes.getSensor("duffelbag/text/00000000000000f4");
        assertNotNull("Sensor object did not create", testingSensor);

        assertEquals(testingSensor.getId(), 0xf4);
        assertEquals(testingSensor.getNodeType(), TEXT);
        assertEquals(testingSensor.getTextValue(), "Test message");
        assertEquals(testingSensor.getMqttTopic(), "duffelbag/text/00000000000000f4");
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
         *      "actuators":[{                      -- array of actuators
         *                      "topic":"actuator's mqtt topic string",
         *                      "name":"actuator's name string",
         *                      "minvalue":"minimum value will be float",
         *                      "maxvalue":"maximum value will be float"
         *                  },{
         *                      ...
         *                      next actuator
         *                      ...
         *                  }],
         *      "sensors":[{                        -- array of sensors
         *                      "topic":"sensor's mqtt topic string",
         *                      "name":"sensor's name",
         *                      "minvalue":"minimum value will be float",
         *                      "maxvalue":"maximum value will be float"
         *                },{
         *                      ...
         *                      next sensor
         *                      ....
         *                }]
         * }
         * */

        String testJSON = "{\"ver\":\"1.0\",\"id\":\"00000000000000f5\",\"name\":\"RGB светильник\",\"location\":\"\",\"actuators\":[{\"topic\":\"duffelbag/rgb/00000000000000f6\",\"name\":\"RGB лампа\",\"minvalue\":\"0.0\",\"maxvalue\":\"1.0\"}],\"sensors\":[{\"topic\":\"duffelbag/voltage/00000000000000f7\",\"name\":\"Входное напряжение\",\"minvalue\":\"0\",\"maxvalue\":\"380\"},{\"topic\":\"duffelbag/power/00000000000000f8\",\"name\":\"Потребляемая мощность\",\"minvalue\":\"0\",\"maxvalue\":\"30\"}]}";

        /* Create and add one sensor, included in this config message, before thing parse */

        MqttMessage mqttMessage1 = new MqttMessage();
        mqttMessage1.setPayload(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(3.2f).array());
        AddOrRefreshNode parseSensor = new AddOrRefreshNode("duffelbag/power/00000000000000f8", mqttMessage1);
        parseSensor.detectDuffelbagNode();
        SensorNode sensorNode1 = allNodes.getSensor("duffelbag/power/00000000000000f8");
        assertNotNull(sensorNode1);
        assertEquals(sensorNode1.getValue(),3.2f,0);

        /* Get THING MQTT packet */

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(testJSON.getBytes());
        AddOrRefreshNode addOrRefreshNode = new AddOrRefreshNode("duffelbag/outlet/00000000000000f5", mqttMessage);
        assertNotNull(addOrRefreshNode);
        addOrRefreshNode.detectDuffelbagNode();

        /* Check Thing object */

        Thing testThing = allNodes.getThing("duffelbag/outlet/00000000000000f5");
        assertNotNull(testThing);
        assertEquals(testThing.getVersion(),"1.0");
        assertEquals(testThing.getId(), 0xf5);
        assertEquals(testThing.getNodeType(), OUTLET);
        assertEquals(testThing.getConfigMessage(), testJSON);
        assertEquals(testThing.getMqttTopic(), "duffelbag/outlet/00000000000000f5");
        assertEquals(testThing.getName(),"RGB светильник");
        assertEquals(testThing.getLocation(),"");

        /* Check first actuator */

        ActuatorNode firstActuator = allNodes.getActuator("duffelbag/rgb/00000000000000f6");
        assertNotNull(firstActuator);
        assertEquals(firstActuator.getId(), 0xf6);
        assertEquals(firstActuator.getNodeType(),RGB);
        assertEquals(firstActuator.getName(),"RGB лампа");
        assertEquals(firstActuator.getMqttTopic(),"duffelbag/rgb/00000000000000f6");
        assertEquals(firstActuator.getMinValue(),0.0f,0);
        assertEquals(firstActuator.getMaxValue(),1.0f,0);
        assertEquals(firstActuator.isKnown(),true);
        assertEquals(firstActuator.getVersion(),"1.0");

        /* Check first sensor */

        SensorNode firstSensor = allNodes.getSensor("duffelbag/voltage/00000000000000f7");
        assertNotNull(firstSensor);
        assertEquals(firstSensor.getId(), 0xf7);
        assertEquals(firstSensor.getNodeType(),VOLTAGE);
        assertEquals(firstSensor.getName(),"Входное напряжение");
        assertEquals(firstSensor.getMqttTopic(),"duffelbag/voltage/00000000000000f7");
        assertEquals(firstSensor.getMinValue(),0.0f,0);
        assertEquals(firstSensor.getMaxValue(),380f,0);
        assertEquals(firstSensor.isKnown(),true);
        assertEquals(firstSensor.getVersion(),"1.0");

        /* Check second sensor (already exist) */

        SensorNode secondSensor = allNodes.getSensor("duffelbag/power/00000000000000f8");
        assertNotNull(secondSensor);
        assertEquals(secondSensor.getId(), 0xf8);
        assertEquals(secondSensor.getNodeType(),POWER);
        assertEquals(secondSensor.getName(),"Потребляемая мощность");
        assertEquals(secondSensor.getMqttTopic(),"duffelbag/power/00000000000000f8");
        assertEquals(secondSensor.getMinValue(),0.0f,0);
        assertEquals(secondSensor.getMaxValue(),30f,0);
        assertEquals(secondSensor.getValue(),3.2f,0);
        assertEquals(secondSensor.isKnown(),true);
        assertEquals(secondSensor.getVersion(),"1.0");
    }
}
package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.03.16.
 */
public class AddOrRefreshNodeTest {

    AddOrRefreshNode addOrRefreshNode;
    MqttMessage mqttMessage;

    @Before
    public void setUp() throws Exception {
        mqttMessage = new MqttMessage();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParseAndDetectDuffelbagNodeSensorFloat() throws Exception {
        addOrRefreshNode = new AddOrRefreshNode("duffelbag/temperature/00000000000000f3",mqttMessage);
    }
}
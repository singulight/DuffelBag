package ru.singulight.duffelbag.mqtt;


import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 04.04.16.
 */
public class MqttDispatcher {


    private String brokerURL = "tcp://192.168.1.68:1883";
    private String serverStringId = "DuffelBagServer";
    private MqttClient mqttClient;

    private static final Logger log = Logger.getLogger(MqttDispatcher.class);

    public void pahoStart() {
        try {
            mqttClient = new MqttClient(brokerURL, serverStringId);
            mqttClient.connect();
            SimpleMqttCallback mqttCallback = new SimpleMqttCallback();
            mqttClient.setCallback(mqttCallback);
            mqttClient.subscribe("#");
        } catch (MqttException e) {
            log.error("Fail to connect to MQTT broker",e);
        }
    }

    public void sendMessage(String topic, Float message) {
        MqttMessage mqttMessage = new MqttMessage(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(message).array());
        try {
            mqttClient.publish(topic,mqttMessage);
        } catch (MqttException e) {
            log.error("Fail to send MQTT message",e);
        }
    }

    public void sendMessage(String topic, String message) {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes(StandardCharsets.UTF_8));
        try {
            mqttClient.publish(topic,mqttMessage);
        } catch (MqttException e) {
            log.error("Fail to send MQTT message", e);
        }
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public String getServerStringId() {
        return serverStringId;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

}

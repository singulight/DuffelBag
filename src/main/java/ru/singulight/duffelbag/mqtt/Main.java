package ru.singulight.duffelbag.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class Main {

    private String brokerURL = "tcp://192.168.1.69:1883";
    private String serverStringId = "DuffelBagServer";
    private MqttClient mqttClient;

    public static void main(String[] args) {
        new Main().doDemo();
    }

    private void doDemo() {
        try {
            mqttClient = new MqttClient(brokerURL, serverStringId);
            mqttClient.connect();
            mqttClient.setCallback(new SimpleMqttCallback());
            mqttClient.subscribe("#");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
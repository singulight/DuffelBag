package ru.singulight.duffelbag.main;

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
        new Main().doMain();
    }

    private void doMain() {
        try {
            mqttClient = new MqttClient(brokerURL, serverStringId);
            mqttClient.connect();
            SimpleMqttCallback mqttCallback = new SimpleMqttCallback();
            mqttClient.setCallback(mqttCallback);
            mqttClient.subscribe("#");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
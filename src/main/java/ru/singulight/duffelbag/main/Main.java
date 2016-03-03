package ru.singulight.duffelbag.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import ru.singulight.duffelbag.mqtt.SimpleMqttCallback;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class Main {

    private String brokerURL = "tcp://192.168.1.69:1883";
    private String serverStringId = "DuffelBagServer";
    private MqttClient mqttClient;

    private Server httpServer;
    private final int httpPort = 8080;

    public static void main(String[] args) {
        Main init = new Main();
        init.pahoStart();
        init.jettyStart();
    }

    private void pahoStart() {
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

    private void jettyStart() {
        httpServer = new Server(httpPort);
        httpServer.setHandler(new ru.singulight.duffelbag.web.MainHandler());
        try {
            httpServer.start();
            httpServer.dumpStdErr();
            httpServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
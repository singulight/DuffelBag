package ru.singulight.duffelbag.main;

import ru.singulight.duffelbag.mqtt.MqttDispatcher;
import ru.singulight.duffelbag.web.InitHttpServer;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class Main {



    public static void main(String[] args) {
        MqttDispatcher mqttDispatcher = new MqttDispatcher();
        mqttDispatcher.pahoStart();
        InitHttpServer httpServer = new InitHttpServer();
        httpServer.jettyStart();
    }
}
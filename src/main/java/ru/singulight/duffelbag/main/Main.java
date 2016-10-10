package ru.singulight.duffelbag.main;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.singulight.duffelbag.mqtt.MqttDispatcher;
import ru.singulight.duffelbag.web.WebServer;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class Main {

    private static ApplicationContext ctx;
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        MqttDispatcher mqttDispatcher = new MqttDispatcher();
        mqttDispatcher.pahoStart();

        WebServer webServer = new WebServer();
        try {
            webServer.start();
            webServer.join();
        } catch (Exception e) {
            log.error("Can't start web server",e);
        }
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
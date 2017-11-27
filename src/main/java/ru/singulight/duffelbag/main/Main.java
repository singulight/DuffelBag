package ru.singulight.duffelbag.main;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.singulight.duffelbag.Interfaces.CreateNodeObserver;
import ru.singulight.duffelbag.dao.CreateDatabase;
import ru.singulight.duffelbag.dao.CreateDatabaseH2;
import ru.singulight.duffelbag.dao.NodeDao;
import ru.singulight.duffelbag.dao.NodeDaoH2;
import ru.singulight.duffelbag.mqtt.MqttDispatcher;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.web.WebServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class Main {

    private static ApplicationContext ctx;
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        CreateDatabase createDatabase = (CreateDatabase) ctx.getBean("createDatabase");
        NodeDao nodeDao = (NodeDao) ctx.getBean("nodeDaoH2");
        MqttDispatcher mqttDispatcher = new MqttDispatcher();
        WebServer webServer = new WebServer();

        createDatabase.create10();
        nodeDao.loadAllNodes();
        mqttDispatcher.pahoStart();
        try {
            webServer.start();
            webServer.join();
        } catch (Exception e) {
            log.error("Can't start web server ",e);
        }
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }


}
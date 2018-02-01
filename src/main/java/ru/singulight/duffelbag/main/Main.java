package ru.singulight.duffelbag.main;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.singulight.duffelbag.actions.AllActions;
import ru.singulight.duffelbag.actions.JavaClassAction;
import ru.singulight.duffelbag.dao.CreateDatabase;
import ru.singulight.duffelbag.dao.NodeDao;
import ru.singulight.duffelbag.messagebus.MessageBus;
import ru.singulight.duffelbag.mqtt.MqttDispatcher;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.nodes.types.NodePurpose;
import ru.singulight.duffelbag.nodes.types.NodeType;
import ru.singulight.duffelbag.web.WebServer;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 05.01.16.
 */
public class Main {

    private static ApplicationContext ctx;
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        CreateDatabase createDatabase = (CreateDatabase) ctx.getBean("createDatabase");
        MqttDispatcher mqttDispatcher = new MqttDispatcher();
        NodeDao nodeDao = (NodeDao) ctx.getBean("nodeDaoH2");
        WebServer webServer = new WebServer();

        /* TEST ---------- TEST ---------- TEST*/
        JavaClassAction act = new JavaClassAction();
        AllActions allActions = AllActions.getInstance();
        allActions.insert(act);
        BaseNode node = new BaseNode();
        node.setId(0x2000000000004375L);
        node.setMqttTopic("duffelbag/swt/2000000000004375");
        node.setPurpose(NodePurpose.SENSOR);
        node.setNodeType(NodeType.SWT);
        AllNodes allNodes = AllNodes.getInstance();
        allNodes.insert(node);
        MessageBus messageBus = MessageBus.getInstance();
        messageBus.registerObserver(node.getId(), act);
        /* TEST ---------- TEST ---------- TEST*/

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
package ru.singulight.duffelbag.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.nodes.SensorNode;
import ru.singulight.duffelbag.nodes.types.NodeType;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 01.09.16.
 */
public class NodeDaoH2Test {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    NodeDaoH2 cdb = (NodeDaoH2) ctx.getBean("nodeDaoH2");

    @Test
    public void AddValueTest() {
        SensorNode node = new SensorNode(1L,"duffelbag/temperature/0000000000000001", NodeType.TEMPERATURE);
        node.setValue(54.25f);
        cdb.saveValue((BaseNode) node);
    }

}
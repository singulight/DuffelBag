package ru.singulight.duffelbag.web.websocket;

import org.junit.Test;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.nodes.types.NodePurpose;
import ru.singulight.duffelbag.nodes.types.NodeType;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 19.05.17
 */
public class AdminSocketTest {
    AllNodes allNodes = AllNodes.getInstance();
    @Test
    public void onMessage() throws Exception {
        AdminSocket adminSocket = new AdminSocket();
        BaseNode node = new BaseNode();
        node.setId(0x20000000000000e4L);
        node.setMqttTopic("duffelbag/voltage/20000000000000e4");
        node.setName("Напряжение");
        node.setVersion("1.0");
        node.setKnown(true);
        node.setNodeType(NodeType.VOLTAGE);
        node.setPurpose(NodePurpose.SENSOR);
        node.setValue("20");
        allNodes.insert(node);

        BaseNode node2 = new BaseNode();
        node2.setId(0x20000000000000e5L);
        node2.setMqttTopic("duffelbag/voltage/20000000000000e5");
        node2.setName("Ток");
        node2.setVersion("1.0");
        node2.setKnown(true);
        node2.setNodeType(NodeType.CURRENT);
        node2.setPurpose(NodePurpose.SENSOR);
        node2.setValue("3");
        allNodes.insert(node2);

        assertEquals(allNodes.allSize(),2);
        ArrayList<BaseNode> nodes = new ArrayList<>();
        nodes.add(node2);
        nodes.add(node);
        assertEquals(allNodes.getAllNodesAsList(),nodes);
        allNodes.getAllNodesAsList().forEach((BaseNode nod) -> {
            System.out.println(nod.getId());
        });
        String str = "{\"token\":25, \"ver\":10, \"verb\":\"read\", \"entity\":\"nodes\", \"param\":\"0\"}";
        System.out.println(str);
        System.out.println(adminSocket.parseClientMessage10(str));
    }

}
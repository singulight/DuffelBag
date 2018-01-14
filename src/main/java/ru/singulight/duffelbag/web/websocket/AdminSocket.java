package ru.singulight.duffelbag.web.websocket;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.singulight.duffelbag.messagebus.MessageBus;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.nodes.types.NodePurpose;
import ru.singulight.duffelbag.nodes.types.NodeType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 10.11.16.
 */
@WebSocket
public class AdminSocket {
    public Session session;
    private static final Logger log = Logger.getLogger(AdminSocket.class);
    private SocketObjects socketObjects = SocketObjects.getInstance();
    private AllNodes allNodes = AllNodes.getInstance();
    private MessageBus messageBus = MessageBus.getInstance();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        SocketObjects.getInstance().join(this);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        try {
            session.getRemote().sendString(parseClientMessage10(message));
        } catch (Exception e) {
            log.error("Bad web client request. Stirng: "+message);
        }
    }

    @OnWebSocketClose
    public void onClose (int statusCode, String reason) {
        SocketObjects.getInstance().leave(this);
        this.session = null;
    }

    public String parseClientMessage10(String msg) throws Exception {
        String response = "";
        JSONParser jsonParser = new JSONParser();
        JSONObject mainObject = (JSONObject) jsonParser.parse(msg);
        Long ver = (Long) mainObject.get("ver");
        Long token = (Long) mainObject.get("token");
        if ( ver >= 10 && ver < 20) {
            /* Entity - nodes */
            if (mainObject.get("entity").equals("nodes")) {
                /* Verb - read */
                if (mainObject.get("verb").equals("read")) {
                    if (mainObject.get("param").equals("byTopic")) {
                        ArrayList<BaseNode> param = new ArrayList<>(1);
                        param.add(allNodes.getNodeByTopic((String) mainObject.get("data")));
                        response = socketObjects.createRemoteNodes(param, token).toJSONString();
                    } else {
                        Long id = new BigInteger((String) mainObject.get("param"), 16).longValue();
                        if (id == 0) {
                            System.out.println(id);
                            response = socketObjects.createRemoteNodes(allNodes.getAllNodesAsList(), token).toJSONString();

                        } else {
                            ArrayList<BaseNode> param = new ArrayList<>(1);
                            param.add(allNodes.getNodeById(id));
                            response = socketObjects.createRemoteNodes(param, token).toJSONString();
                        }
                    }
                }
                /* Verb - update */
                if (mainObject.get("verb").equals("update")) {
                    // if (mainObject.get("param").equals("normal")) {}
                    JSONObject nodeObject = (JSONObject) mainObject.get("data");
                    BaseNode node = allNodes.getNodeByTopic((String) nodeObject.get("topic"));

                    /* Update exists */
                    if (node != null) {
                        node.setName((String) nodeObject.get("name"));
                        node.setKnown((Boolean) nodeObject.get("known"));
                        try {
                            node.setNodeType(NodeType.valueOf((String) nodeObject.get("type")));
                        } catch (Exception e) {
                            node.setNodeType(NodeType.OTHER);
                        }
                        try {
                            node.setPurpose(NodePurpose.valueOf((String) nodeObject.get("purpose")));
                        } catch (Exception e) {
                            node.setPurpose(NodePurpose.UNKNOWN);
                        }
                        Map<String, String> options = new HashMap<>();
                        JSONArray rawOptions = (JSONArray) nodeObject.get("options");
                        for (Object rawOption : rawOptions) {
                            JSONObject slide = (JSONObject) rawOption;
                            String key = (String) slide.get("key");
                            String value = (String) slide.get("value");
                            options.put(key, value);
                        }
                        node.deleteAllOptions();
                        node.setOptions(options);
                        messageBus.onConfigUpdateEvent(node);

                    } else {
                        /* Create new */
                        node = new BaseNode();
                        node.setVersion("1.0");
                        node.setId(0L);
                        node.setMqttTopic((String) nodeObject.get("topic"));
                        node.setName((String) nodeObject.get("name"));
                        node.setKnown((Boolean) nodeObject.get("known"));
                        try {
                            node.setNodeType(NodeType.valueOf((String) nodeObject.get("type")));
                        } catch (Exception e) {
                            node.setNodeType(NodeType.OTHER);
                        }
                        try {
                            node.setPurpose(NodePurpose.valueOf((String) nodeObject.get("purpose")));
                        } catch (Exception e) {
                            node.setPurpose(NodePurpose.UNKNOWN);
                        }
                        Map<String, String> options = new HashMap<>();
                        JSONArray rawOptions = (JSONArray) nodeObject.get("options");
                        for (Object rawOption : rawOptions) {
                            JSONObject slide = (JSONObject) rawOption;
                            String key = (String) slide.get("key");
                            String value = (String) slide.get("value");
                            options.put(key, value);
                        }
                        node.setOptions(options);
                        //TODO: parse actions from JSON
                    }
                }
            }
            /* Entity - actions */
            if (mainObject.get("entity").equals("actions")) {

            }
        }
        return response;
    }
}

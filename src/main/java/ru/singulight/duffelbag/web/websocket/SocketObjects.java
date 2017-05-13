package ru.singulight.duffelbag.web.websocket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.singulight.duffelbag.nodes.BaseNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 06.01.17.
 */
public class SocketObjects {
    private static SocketObjects ourInstance = new SocketObjects();

    public static SocketObjects getInstance() {
        return ourInstance;
    }

    private SocketObjects() {
    }

    private ArrayList<AdminSocket> sockets = new ArrayList<>();

    public void join(AdminSocket socket) {
        sockets.add(socket);
    }

    public void leave(AdminSocket socket) {
        sockets.remove(socket);
    }

    public void send(String message) {
        for (AdminSocket socket: sockets) {
            try {
                socket.session.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createRemoteNode(ArrayList<BaseNode> nodes, int token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        jsonObject.put("ver",10);
        jsonObject.put("verb","create");
        jsonObject.put("entity","nodes");
        JSONArray jsonNodes = new JSONArray();
        nodes.forEach((BaseNode node) -> {
            jsonNodes.add(nodeToJSON(node));
        });
        this.send(jsonObject.toJSONString());
    }

    public JSONObject nodeToJSON(BaseNode node) {
        JSONObject result = new JSONObject();
        result.put("ver",10);
        result.put("id",node.getId());
        result.put("topic",node.getMqttTopic());
        result.put("name",node.getName());
        result.put("known",node.isKnown());
        result.put("type",node.getNodeType().toString());
        result.put("purpose",node.getPurpose().toString());
        result.put("value",node.getValue());
        JSONArray options = new JSONArray();
        Map<String, String> nodeOptions = node.getOptions();
        nodeOptions.forEach((String key, String value) -> {
            JSONObject option = new JSONObject();
            option.put("key",key);
            option.put("value",value);
            options.add(option);
        });
        result.put("options",options);
        JSONArray actions = new JSONArray();
        node.getObserversIds().forEach((Integer id) -> {
            JSONObject action = new JSONObject();
            action.put("id",id);
            actions.add(action);
        });
        result.put("actions",actions);
        return result;
    }
}

package ru.singulight.duffelbag.web.websocket;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.singulight.duffelbag.actions.Action;
import ru.singulight.duffelbag.actions.AllActions;
import ru.singulight.duffelbag.messagebus.MessageBus;
import ru.singulight.duffelbag.messagebus.NodeEventObserver;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.singulight.duffelbag.messagebus.MessageBus.*;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 06.01.17.
 */
public class SocketObjects implements NodeEventObserver {

    private static final Logger log = Logger.getLogger(SocketObjects.class);
    private static SocketObjects ourInstance = new SocketObjects();
    private AllActions allActions = AllActions.getInstance();
    private AllNodes allNodes = AllNodes.getInstance();

    public static SocketObjects getInstance() {
        return ourInstance;
    }

    private SocketObjects() {
        messageBus.registerObserver(ALL_OBSERVABLES, this);

    }

    private MessageBus messageBus = MessageBus.getInstance();

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

    public JSONObject createRemoteNodes(List<BaseNode> nodes, Long token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        jsonObject.put("ver",10);
        jsonObject.put("verb","create");
        jsonObject.put("entity","nodes");
        JSONArray jsonNodes = new JSONArray();
        nodes.forEach((BaseNode node) -> {
            jsonNodes.add(nodeToJSON(node));
        });
        jsonObject.put("data",jsonNodes);
        return jsonObject;
    }

    public JSONObject updateValueRemoteNode(BaseNode node, Long token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        jsonObject.put("ver",10);
        jsonObject.put("verb","update");
        jsonObject.put("entity","nodes");
        jsonObject.put("param", longToJSON(node.getId()));
        jsonObject.put("data",node.getValue());
        return jsonObject;
    }

    public JSONObject createRemoteActions(List<Action> actions, Long token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        jsonObject.put("ver",10);
        jsonObject.put("verb","create");
        jsonObject.put("entity","actions");
        JSONArray jsonActions = new JSONArray();
        actions.forEach(a -> jsonActions.add(actionToJSON(a)));
        jsonObject.put("data",jsonActions);
        return jsonObject;
    }

    public JSONObject nodeToJSON(BaseNode node) {
        JSONObject result = new JSONObject();
        if (node != null) {
            result.put("ver", 10);
            result.put("id", longToJSON(node.getId()));
            result.put("topic", node.getMqttTopic());
            result.put("name", node.getName());
            result.put("known", node.isKnown());
            result.put("type", node.getNodeType().toString());
            result.put("purpose", node.getPurpose().toString());
            result.put("value", node.getValue());
            JSONArray options = new JSONArray();
            Map<String, String> nodeOptions = node.getOptions();
            nodeOptions.forEach((String key, String value) -> {
                JSONObject option = new JSONObject();
                option.put("key", key);
                option.put("value", value);
                options.add(option);
            });
            result.put("options", options);
            JSONArray actions = new JSONArray();
            List observers = messageBus.getObserversForObservable(node.getId());
            if (observers != null) {
                observers.forEach(o -> {
                    JSONObject action = new JSONObject();
                    try {
                        action.put("id",((Action) o).getActionId());
                        action.put("type",((Action) o).getActionType().toString());
                        action.put("desc",((Action) o).getDescription());
                    } catch (ClassCastException e) {
                        log.error("SocketObjects: observer " + o.toString() + " is not action.");
                    }
                    actions.add(action);
                });
            }
            result.put("actions", actions);
        }
        return result;
    }

    public JSONObject actionToJSON(Action action) {
        JSONObject result = new JSONObject();
        result.put("ver", 10);
        result.put("id", action.getActionId());
        result.put("type", action.getActionType().toString());
        result.put("desc", action.getDescription());
        JSONArray initiators = new JSONArray();
        List<Long> observables = messageBus.getObservablesForObserver(action);
        if (observables != null) {
            observables.forEach(l -> {
                BaseNode node = allNodes.getNodeById(l);
                if (node != null) {
                    initiators.add(node.getMqttTopic());
                } else {
                    log.warn("Specified in the action id: "+action.getActionId()+" initiator id: "+l+" does not exist");
                }
            });
        }
        result.put("initiators", initiators);

        return result;
        // TODO: send more information to web client. Different "data" for each of class of actions
    }

    private Long longToJSON(Long num) {
        return num & 0x00000000FFFFFFFFL; //TODO: do something for javascript (all numbers are double)
    }

    @Override
    public void nodeEvent(BaseNode observable, int reason) {
        switch (reason) {
            case CREATE:
                ArrayList<BaseNode> nodes = new ArrayList<>(1);
                nodes.add(observable);
                send(createRemoteNodes(nodes,0L).toJSONString());
                break;
            case VALUE_UPD:
                send(updateValueRemoteNode(observable, 0L).toJSONString());
                break;
            case CONFIG_UPD:
                break;
            case DEL:
                break;
        }
    }

    @Override
    public int observerType() {
        return WEB;
    }
}

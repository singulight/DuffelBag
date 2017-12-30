package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.messagebus.CreateNodeObserver;
import ru.singulight.duffelbag.messagebus.MessageBus;

import java.util.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Array of all nodes, actuators and things
 */
public class AllNodes {

    private AllNodes() {

    }

    private static AllNodes ourInstance = new AllNodes();
    public static AllNodes getInstance() {
        return ourInstance;
    }

    private MessageBus messageBus = MessageBus.getInstance();

    private static Map<Long, BaseNode> allNodes = new Hashtable<>();
    /** Topic to Id map */
    private static Map<String, Long> topicIdMap = new Hashtable<>();

    /*Getter and setters*/
    public void insert(BaseNode node) {
        allNodes.put(node.getId(), node);
        topicIdMap.put(node.getMqttTopic(), node.getId());
        messageBus.onCreateEvent(node);
    }

    public  void delete(BaseNode node) {
        messageBus.onDeleteEvent(node);
        Long id = node.getId();
        topicIdMap.remove(allNodes.get(id).getMqttTopic());
        allNodes.remove(id);
    }
    public void delete (Long id) {
        delete(allNodes.get(id));
    }
    public boolean ifExists(Long id) {
        return allNodes.containsKey(id);
    }
    public boolean ifTopicExists(String topic) {
        return topicIdMap.containsKey(topic);
    }
    public BaseNode getNodeById(Long id) {
        return allNodes.get(id);
    }
    public BaseNode getNodeByTopic(String topic) {
        Long id = topicIdMap.get(topic);
        if (id == null) return null;
        return allNodes.get(id);
    }
    public int allSize() {
        return allNodes.size();
    }

    public ArrayList<BaseNode> getAllNodesAsList() {
        return new ArrayList<>(allNodes.values());
    }
}

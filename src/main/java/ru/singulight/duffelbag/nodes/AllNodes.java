package ru.singulight.duffelbag.nodes;

import java.util.Hashtable;
import java.util.Map;

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

    private static Map<Long, BaseNode> allNodes = new Hashtable<>();
    private static Map<String, Long> topicIdMap = new Hashtable<>();

    /*Getter and setters*/
    public void insert(BaseNode node) {
        allNodes.put(node.getId(), node);
        topicIdMap.put(node.getMqttTopic(), node.getId());
    }
    public  void delete(Long id) {
        topicIdMap.remove(allNodes.get(id).getMqttTopic());
        allNodes.remove(id);
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
}

package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.messagebus.CreateNodeObserver;

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

    private static Map<Long, BaseNode> allNodes = new Hashtable<>();
    /** Topic to Id map */
    private static Map<String, Long> topicIdMap = new Hashtable<>();
    /** Create node createNodeObservers */
    private static List<CreateNodeObserver> createNodeObservers = new LinkedList<>();

    /*Getter and setters*/
    public void insert(BaseNode node) {
        allNodes.put(node.getId(), node);
        topicIdMap.put(node.getMqttTopic(), node.getId());
        notifyCreateNodeObservers(node);
//        ArrayList<BaseNode> nodes = new ArrayList<>(1);
//        nodes.add(node);
//        socketObjects.send(socketObjects.createRemoteNodes(nodes,0L).toJSONString());
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

    public ArrayList<BaseNode> getAllNodesAsList() {
        return new ArrayList<>(allNodes.values());
    }

    public void registerCreateNodeObserver(CreateNodeObserver o) {
        createNodeObservers.add(o);
    }
    public void removeCreateNodeObserver(CreateNodeObserver o) {
        createNodeObservers.remove(o);
    }
    private void notifyCreateNodeObservers(BaseNode node) {
        createNodeObservers.forEach((CreateNodeObserver o) -> o.createNodeEvent(node));
    }
}

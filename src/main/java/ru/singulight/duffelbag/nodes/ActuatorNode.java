package ru.singulight.duffelbag.nodes;
import ru.singulight.duffelbag.nodes.types.*;
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 */
public class ActuatorNode extends Node {

    public ActuatorNode(long id, String topic, NodeType nodeType) {
        super.id = id;
        super.mqttTopic = topic;
        super.nodeType = nodeType;
    }

}

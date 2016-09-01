package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.nodes.types.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 17.12.15.
 * @version 0.1
 *
 *
 */
public class SensorNode extends Node {

    public SensorNode() {
    }

    public SensorNode(long sensorId, String mqttTopic, NodeType type) {
        super.id = sensorId;
        super.mqttTopic = mqttTopic;
        super.nodeType = type;
    }


}

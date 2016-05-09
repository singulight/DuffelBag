package ru.singulight.duffelbag.nodes;
import ru.singulight.duffelbag.nodes.types.*;
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 */
public class ActuatorNode extends BaseNode {

    private float value;
    private float minValue = 0;
    private float maxValue = 100.0f;

    public ActuatorNode(long id, String topic, NodeType nodeType) {
        super.id = id;
        super.mqttTopic = topic;
        super.nodeType = nodeType;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

}

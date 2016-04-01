package ru.singulight.duffelbag.mqttnodes;

import ru.singulight.duffelbag.mqttnodes.types.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 17.12.15.
 * @version 0.1
 *
 *
 */
public class SensorNode extends BaseNode {

    public SensorNode() {
    }

    public SensorNode(long sensorId, String mqttTopic, NodeType type) {
        super.id = sensorId;
        super.mqttTopic = mqttTopic;
        super.nodeType = type;
    }

    /** Sensor value if no NodeType.TEXT type. Must be synchronized to remote sensor.*/
    private float value = 0.0f;
    /** Minimum value of sensor */
    private float minValue = 0.0f;
    /** Maximum value of sensor */
    private float maxValue = 100.0f;
    /** Sensor value if NodeType.TEXT type. Must be synchronized to remote sensor.*/
    private String textValue = "";


/*Getters and Setters */

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
        observable.notifyObservers();
    }

    public float getMinValue() {
        return minValue;
    }
    public void setMinValue (float minValue) { this.minValue = minValue; }

    public float getMaxValue() {
        return maxValue;
    }
    public void setMaxValue(float maxValue) { this.maxValue = maxValue; }

    public String getTextValue() {
        return textValue;
    }
    public void setTextValue(String textValue) { this.textValue = textValue; }


}

package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.nodes.types.NodeType;

import java.util.Observable;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Superclass for each sensor, actuator and thing
 */
public class BaseNode {

    public BaseNode(long sensorId, String mqttTopic, NodeType type) {
        this.id = sensorId;
        this.mqttTopic = mqttTopic;
        this.nodeType = type;
    }

    public BaseNode() {

    }


    /** Sensor id*/
    protected Long id;
    /** MQTT address for this node */
    protected String mqttTopic;

    protected String name;
    /** Duffelbag node version*/
    protected String version;
    /** True if all the parameters are known */
    protected boolean known = false;
    /** Note type */
    protected NodeType nodeType;
    /** Sensor value if no NodeType.TEXT type. Must be synchronized to remote sensor.*/
    private float value = 0.0f;
    /** Minimum value of sensor */
    private float minValue = 0.0f;
    /** Maximum value of sensor */
    private float maxValue = 100.0f;
    /** Sensor value if NodeType.TEXT type. Must be synchronized to remote sensor.*/
    private String textValue = "";
    /** Observable object */
    protected Observable observable = new Observable();

    public NodeType getNodeType() {
        return nodeType;
    }
    public long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getMqttTopic() {
        return mqttTopic;
    }
    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public boolean isKnown() {
        return known;
    }
    public void setKnown(boolean known) {
        this.known = known;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

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

    public Observable getObservable() {
        return observable;
    }
    public void setObservable(Observable observable) {
        this.observable = observable;
    }

}

package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.messagebus.MessageBus;
import ru.singulight.duffelbag.messagebus.Observable;
import ru.singulight.duffelbag.messagebus.UpdateValueObserver;
import ru.singulight.duffelbag.nodes.types.NodePurpose;
import ru.singulight.duffelbag.nodes.types.NodeType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Superclass for each sensor, actuator and thing
 */
public class BaseNode  {

    public BaseNode(long sensorId, String mqttTopic, NodeType type) {
        this.id = sensorId;
        this.mqttTopic = mqttTopic;
        this.nodeType = type;
    }

    public BaseNode() {
    }

    protected MessageBus messageBus = MessageBus.getInstance();

    /** Sensor id*/
    protected Long id = 0L;
    /** MQTT address for this node */
    protected String mqttTopic;

    protected String name = "";
    /** Duffelbag node version*/
    protected String version = "0";
    /** True if all the parameters are known */
    protected boolean known = false;
    /** Note type */
    protected NodeType nodeType = NodeType.OTHER;
    /** Node purpose */
    protected NodePurpose purpose = NodePurpose.UNKNOWN;
    /** Sensor value. Must be synchronized to remote sensor.*/
    protected String value = "";
    /*  Raw value. Array of bytes */
    protected byte[] rawValue = {0};
    /** Strategy save values to database */
    protected Strategy notifyUpdateStategy = Strategy.EACH;
    /* Set of properties like min, max value and other options*/
    protected Map<String, String> options = new HashMap<>();
    /**
    * Getters and setters
    * */
    public NodePurpose getPurpose() {
        return purpose;
    }
    public void setPurpose(NodePurpose purpose) {
        this.purpose = purpose;
    }

    public NodeType getNodeType() {
        return nodeType;
    }
    public void setNodeType(NodeType nodeType) {this.nodeType = nodeType; }

    public long getId() {
        return id;
    }
    public void setId(Long id) {
        if (id != null) this.id = id;
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

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public byte[] getRawValue() {
        return rawValue;
    }
    public void setRawValue(byte[] rawValue) {
        this.rawValue = rawValue;
        messageBus.onUpdateEvent(this);
    }
    public Strategy getNotifyUpdateStategy() {
        return notifyUpdateStategy;
    }
    public void setNotifyUpdateStategy(Strategy notifyUpdateStategy) {
        this.notifyUpdateStategy = notifyUpdateStategy;
    }

    public Map getOptions() {
        return options;
    }
    public void setOptions(Map<String, String> options) {this.options = options;}
    public void deleteAllOptions() {
        options.clear();
    }
    public void setOption(String key, String value) {
        this.options.put(key, value);
    }
    public void deleteOption(String key) {
        this.options.remove(key);
    }
    public boolean ifOptionExists(String key) {
        return this.options.containsKey(key);
    }


    public enum Strategy {
        EACH,
        PERIODICALLY_LAST,
        PERIODICALLY_AVERAGE,
        PERIODICALLY_MIN,
        PERIODICALLY_MAX,
        PERIODICALLY_MIN_MAX
    }
}

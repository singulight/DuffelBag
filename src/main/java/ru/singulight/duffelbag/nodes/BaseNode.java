package ru.singulight.duffelbag.nodes;

import org.assertj.core.internal.Bytes;
import ru.singulight.duffelbag.actions.Observer;
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
public class BaseNode implements Observable{

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

    protected String name = "";
    /** Duffelbag node version*/
    protected String version = "0";
    /** True if all the parameters are known */
    protected boolean known = false;
    /** Note type */
    protected NodeType nodeType = NodeType.OTHER;
    /* Node purpose */
    protected NodePurpose purpose = NodePurpose.UNKNOWN;
    /** Sensor value. Must be synchronized to remote sensor.*/
    private String value = "";
    /*  Raw value. Array of bytes */
    protected byte[] rawValue;
    /* Set of properties like min, max value and other options*/
    protected Map<String, String> options = new HashMap<>();
    /** Observers */
    protected List<Observer> observers = new LinkedList<>();

    /*
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

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
        notifyObservers();
    }
    public byte[] getRawValue() {
        return rawValue;
    }
    public void setRawValue(byte[] rawValue) {
        this.rawValue = rawValue;
        notifyObservers();
    }

    public Map getOptions() {
        return options;
    }
    public void setOptions(Map<String, String> options) {this.options = options;}
    public void setOption(String key, String value) {
        this.options.put(key, value);
    }
    public void deleteOption(String key) {
        this.options.remove(key);
    }
    public boolean ifOptionExists(String key) {
        return this.options.containsKey(key);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        observers.forEach((Observer o) -> {
            o.update(this);
        });
    }

    @Override
    public List<Integer> getObserversIds() {
        List<Integer> result = new LinkedList<>();
        observers.forEach((Observer o) -> {
            result.add(o.getId());
        });
        return result;
    }

}

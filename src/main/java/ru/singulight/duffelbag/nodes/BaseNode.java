package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.nodes.types.NodeType;

import java.util.Observable;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Superclass for each sensor, actuator and thing
 */
public abstract class BaseNode {

    /** Sensor id*/
    protected long id;
    /** Sensor name string */
    protected String name;
    /** MQTT address for this node */
    protected String mqttTopic;
    /** Duffelbag node version*/
    protected String version;
    /** True if all the parameters are known */
    protected boolean known = false;
    /** Note type */
    protected NodeType nodeType;
    /** Observable object */
    protected Observable observable = new Observable();

    public NodeType getNodeType() {
        return nodeType;
    }
    public long getId() {
        return id;
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

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

}

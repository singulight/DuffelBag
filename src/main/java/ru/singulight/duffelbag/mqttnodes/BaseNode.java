package ru.singulight.duffelbag.mqttnodes;

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
    /** True if all the parameters are known */
    protected boolean known;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
}
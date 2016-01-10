package ru.singulight.duffelbag.sensors;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Superclass for each sensor, actuator and thing
 */
public abstract class EachMqttNode {

    /** Sensor id*/
    protected long id;
    /** Sensor name string */
    protected String name;
    /** MQTT address for this sensor */
    protected String mqttTopic;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }



}

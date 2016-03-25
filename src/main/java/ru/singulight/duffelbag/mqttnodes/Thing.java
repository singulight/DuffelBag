package ru.singulight.duffelbag.mqttnodes;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import ru.singulight.duffelbag.mqttnodes.types.*;
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 *
 */
public class Thing extends BaseNode {

    private String configMessage;
    private String location;
    private Map<String, SensorNode> sensors = new Hashtable<>();
    private Map<String, ActuatorNode> actuators = new Hashtable<>();

    public Thing (long sensorId, String mqttTopic, NodeType type) {
        super.id = sensorId;
        super.mqttTopic = mqttTopic;
        super.nodeType = type;
    }

    public String getConfigMessage() {
        return configMessage;
    }
    public void setConfigMessage(String configMessage) {
        this.configMessage = configMessage;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public void addSensor (SensorNode sensor) {
        sensors.put(sensor.getMqttTopic(), sensor);
    }

    public void addActuator (ActuatorNode actuator) {
        actuators.put(actuator.getMqttTopic(), actuator);
    }

    public void removeSensor (String topic) {
        sensors.remove(topic);
    }

    public void removeActuator (String topic) {
        actuators.remove(topic);
    }
}

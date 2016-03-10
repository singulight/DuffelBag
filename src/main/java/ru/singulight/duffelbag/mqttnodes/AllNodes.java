package ru.singulight.duffelbag.mqttnodes;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Array of all mqttnodes, actuators and things
 */
public class AllNodes {

    private AllNodes() {

    }

    private static AllNodes ourInstance = new AllNodes();

    public static AllNodes getInstance() {
        return ourInstance;
    }


    public static Map<String, SensorNode> allSensors = new Hashtable<>();
    public static Map<String, ActuatorNode> allActuators = new Hashtable<>();
    public static Map<String, Thing> allThings = new Hashtable<>();

    public boolean isSensorExist(String topic) {
        return allSensors.containsKey(topic);
    }

    public boolean isActuatorExist(String topic) {
        return  allActuators.containsKey(topic);
    }

    public boolean isThingExist(String topic) {
        return allThings.containsKey(topic);
    }

    public void addSensor (SensorNode sensor) {
        allSensors.put(sensor.getMqttTopic(), sensor);
    }

    public void addActuator(ActuatorNode actuator) {
        allActuators.put(actuator.getMqttTopic(), actuator);
    }

    public void addThing(Thing thing) {
        allThings.put(thing.getMqttTopic(), thing);
    }

    public SensorNode getSensor(String topic) {
        return (SensorNode) allSensors.get(topic);
    }

    /**
     * @return count of registered sensors
     **/
    public int sensorsSize() {
        return allSensors.size();
    }
    /**
     * @return count of registered actuators
     **/
    public int actuatorsSize() {
        return allActuators.size();
    }
    /**
     * @return count of registered things
     **/
    public int thingsSize() {
        return allThings.size();
    }
    /**
     * @return count of all registered nodes
     **/
    public int allSize() {
        return sensorsSize()+actuatorsSize()+thingsSize();
    }

}

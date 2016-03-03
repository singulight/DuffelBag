package ru.singulight.duffelbag.mqttnodes;

import java.util.LinkedList;
import java.util.List;

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

    public static List <SensorNode> allSensors = new LinkedList<>();
    public static List <ActuatorNode> allActuators = new LinkedList<>();
    public static List <Thing> allThings = new LinkedList<>();

    /** Add sensor to array if not exist
     * @param sens add sensor if his MQTT address not exist in array
     **/
    public void addSensor(SensorNode sens) {
        int i = 0;
        for (SensorNode eachSensor : allSensors) {
            if (eachSensor.getMqttTopic().equals(sens.getMqttTopic())) {
                i++;
            }
        }
        if (i == 0) allSensors.add(sens);
    }
    /** Add actuator to array if not exist
     * @param actu add actuator if his MQTT address not exist in array
     **/
    public void addActuator(ActuatorNode actu) {
        int i = 0;
        for (ActuatorNode actuatorNode : allActuators) {
            if(actuatorNode.getMqttTopic().equals(actu.getMqttTopic())) {
                i++;
            }
        }
        if (i == 0) allActuators.add(actu);
    }
    /** Add thing to array if not exist
     * @param thing add this if his MQTT address not exist in array
     **/
    public void addThing(Thing thing) {
        int i = 0;
        for (Thing eachThing : allThings) {
            if(eachThing.getMqttTopic().equals(thing.getMqttTopic())) {
                i++;
            }
        }
        if (i == 0) allThings.add(thing);
    }
    /**
     * @return count of registered mqttnodes
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

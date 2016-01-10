package ru.singulight.duffelbag.sensors;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 10.01.16.
 * Array of all sensors, actuators and things
 */
public class AllMqttNodes {

    private AllMqttNodes() {

    }

    private static AllMqttNodes ourInstance = new AllMqttNodes();

    public static AllMqttNodes getInstance() {
        return ourInstance;
    }

    private static List <EachSensor> allSensors = new LinkedList<>();
    private static List <EachActuator> allActuators = new LinkedList<>();
    private static List <Thing> allThings = new LinkedList<>();

    /** Add sensor to array if not exist
     * @param sens add sensor if his MQTT address not exist in array
     **/
    public void addSensor(EachSensor sens) {
        int i = 0;
        for (EachSensor eachSensor : allSensors) {
            if (eachSensor.getMqttTopic().equals(sens.getMqttTopic())) {
                i++;
            }
        }
        if (i == 0) allSensors.add(sens);
    }
    /** Add actuator to array if not exist
     * @param actu add actuator if his MQTT address not exist in array
     **/
    public void addActuator(EachActuator actu) {
        int i = 0;
        for (EachActuator eachActuator : allActuators) {
            if(eachActuator.getMqttTopic().equals(actu.getMqttTopic())) {
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

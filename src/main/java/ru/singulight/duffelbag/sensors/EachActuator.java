package ru.singulight.duffelbag.sensors;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 */
public class EachActuator {

    public enum ActuatorType {
        RGB,
        RELAY,
        VOLTAGE
  }

    private String name;
    private String mqttPrefix;
    private ActuatorType type = ActuatorType.VOLTAGE;
    private float value;
    private float minValue = 0;
    private float maxValue = 100.0f;
}
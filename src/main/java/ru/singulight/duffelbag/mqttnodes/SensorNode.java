package ru.singulight.duffelbag.mqttnodes;

import ru.singulight.duffelbag.mqttnodes.actions.ISensorAction;
import ru.singulight.duffelbag.mqttnodes.types.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 17.12.15.
 * @version 0.1
 *
 *
 */
public class SensorNode extends BaseNode {

    public SensorNode() {

    }

    public SensorNode(long sensorId, String name, String mqttTopic, SensorType type, float minValue, float maxValue){
        super.id = sensorId;
        super.name = name;
        super.mqttTopic = mqttTopic;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }




    /** Sensor type enum. Default value is "SensorType.VOLTAGE" */
    private SensorType type = SensorType.VOLTAGE;
    /** Sensor value if no SensorType.TEXT type. Must be synchronized to remote sensor.*/
    private float value = 0.0f;
    /** Minimum value of sensor */
    private float minValue = 0.0f;
    /** Maximum value of sensor */
    private float maxValue = 100.0f;
    /** Sensor value if SensorType.TEXT type. Must be synchronized to remote sensor.*/
    private String textValue = "";
    /** Collection of actions. Each sensor has no, one or many actions. Each action acts on the one actuator.*/
    private List<ISensorAction> sensorActions = new LinkedList<>();

    /**
     * Run all actions. Each sensor has no, one or many actions. Each action acts on the one actuator.
     * This method runs all actions attached to this sensor.
     */
    public void actionsGo() {
        sensorActions.forEach((action) -> {
            if (type == SensorType.TEXT) {
                action.go(textValue);
            } else {
                action.go(value);
            }
        });
    }

/*Getters and Setters */

    public float getValue() {
        return value;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void configure(long sensorId,String name,String mqttTopic,SensorType type,float minValue,float maxValue) {
        super.id = sensorId;
        super.name = name;
        super.mqttTopic = mqttTopic;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void addAction(ISensorAction action) {
        this.sensorActions.add(action);
    }

    public void removeAction(ISensorAction action) {
        sensorActions.remove(action);
    }

    public List<ISensorAction> getSensorActions() {
        return this.sensorActions;
    }

}

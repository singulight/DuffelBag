package ru.singulight.duffelbag.mqttnodes;

import java.util.ArrayList;
import ru.singulight.duffelbag.mqttnodes.types.*;
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 *
 */
public class Thing extends BaseNode {

    private String configMessage;
    private ArrayList<SensorNode> sensors = new ArrayList<>();
    private ArrayList<ActuatorNode> actuators = new ArrayList<>();


    public String getConfigMessage() {
        return configMessage;
    }

    public void setConfigMessage(String configMessage) {
        this.configMessage = configMessage;
    }
}

package ru.singulight.duffelbag.mqttnodes;

import java.util.ArrayList;
import ru.singulight.duffelbag.mqttnodes.types.*;
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 *
 */
public class Thing extends BaseNode {


    private String name;
    private String mqttPrefix;
    private ThingType type = ThingType.RELAY;
    private ArrayList<SensorNode> sensors = new ArrayList<>();
    private ArrayList<ActuatorNode> actuators = new ArrayList<>();
}

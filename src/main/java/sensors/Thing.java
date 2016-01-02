package sensors;

import java.util.ArrayList;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 *
 */
public class Thing {

    enum type {
        RGB_LAMP,
        RELAY,
        DIMMER,
        SWITCH
    }


    private String name;
    private String mqttPrefix;
    private ArrayList<EachSensor> sensors = new ArrayList<>();
    private ArrayList<EachActuator> actuators = new ArrayList<>();
}

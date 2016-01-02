package sensors;

import java.util.ArrayList;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 *
 */
public class Thing {

    enum type {
        rgbLamp,
        relay,
        dimmer
    }

    private String name;
    private String mqttPrefix;
    private ArrayList<EachSensor> sensors = new ArrayList<>();
    private ArrayList<EachActuator> actuators = new ArrayList<>();
}

package sensors;

import java.util.ArrayList;

/**
 * Created by init on 18.12.15.
 */
public class Thing {

    enum type {
        rgbLamp,
        relay,
        dimmer
    };
    private String name;
    private String mqttPrefix;
    private ArrayList<EachSensor> sensors = new ArrayList<EachSensor>();
    private ArrayList<EachActuator> actuators = new ArrayList<EachActuator>();
}

package sensors;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by init on 17.12.15.
 */
public class EachSensor {

    public enum SensorType {
        TEMPERATURE,
        REL_HUMIDITY,
        ATMOSPHERIC_PRESSURE,
        RAINFALL,
        WIND_SPEED,
        POWER,
        POWER_CONSUMPTION,
        VOLTAGE,
        WATER_FLOW,
        WATER_CONSUMPTION,
        RESISTANCE,
        GAS_CONCENTRATION,
        PUSH_BUTTON,
        SWT,
        OTHER,
        TEXT
    };

    // Sensor description and state
    private String name;
    private String mqttPrefix;
    private SensorType type = SensorType.VOLTAGE;
    private float value;                //Value if no TEXT type sensor
    private float minValue = 0.0f;
    private float maxValue = 100.0f;
    private String textValue = "";      //Value if TEXT type sensor
    private List<ISensorAction> sensorActions = new LinkedList<ISensorAction>();


    public void actionsGo() {
        sensorActions.forEach((action) -> {
            if (type == SensorType.TEXT) {
                action.go(textValue);
            } else {
                action.go(value);
            }
        });
    }
}

package ru.singulight.duffelbag.mqttnodes;
import ru.singulight.duffelbag.mqttnodes.types.*;
/**
 * Created by Grigorii Nizovoi info@singulight.ru on 18.12.15.
 */
public class ActuatorNode extends BaseNode {

    private ActuatorType type = ActuatorType.VOLTAGE;
    private float value;
    private float minValue = 0;
    private float maxValue = 100.0f;
}

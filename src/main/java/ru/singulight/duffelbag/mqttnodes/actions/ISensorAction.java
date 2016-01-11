package ru.singulight.duffelbag.mqttnodes.actions;

import ru.singulight.duffelbag.mqttnodes.ActuatorNode;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 29.12.15.
 */
public interface ISensorAction {

    void setActionId(int actionId);
    int getActionId();

    void setTargetActuator (ActuatorNode targetActuator);
    ActuatorNode getTargetActuator();

    void go(float value);
    void go(String value);
}

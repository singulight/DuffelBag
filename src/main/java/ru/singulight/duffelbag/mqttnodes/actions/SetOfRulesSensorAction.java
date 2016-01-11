package ru.singulight.duffelbag.mqttnodes.actions;

import ru.singulight.duffelbag.mqttnodes.ActuatorNode;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 29.12.15.
 */
public class SetOfRulesSensorAction implements ISensorAction {

    private int actionId;
    private ActuatorNode targetActuator;

    @Override
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public int getActionId() {
        return actionId;
    }

    @Override
    public void setTargetActuator(ActuatorNode ta) {
        this.targetActuator = ta;
    }

    @Override
    public ActuatorNode getTargetActuator() {
        return this.targetActuator;
    }

    @Override
    public void go(float value) {

    }

    @Override
    public void go(String value) {

    }
}

package ru.singulight.duffelbag.actions;

import ru.singulight.duffelbag.messagebus.NodeEventObserver;
import ru.singulight.duffelbag.nodes.BaseNode;

import static ru.singulight.duffelbag.actions.ActionType.*;
import static ru.singulight.duffelbag.messagebus.MessageBus.ACTION;
import static ru.singulight.duffelbag.messagebus.MessageBus.VALUE_UPD;

/**
 * Created by Grigorii info@singulight.ru on 02.11.17.
 */
public abstract class Action implements NodeEventObserver {
    protected Integer actionId = 0;
    protected String description = "";
    protected ActionType actionType = JAVACLASS;
    abstract void doAction(BaseNode node);

    public Integer getActionId() {
        return actionId;
    }
    public String getDescription() {
        return description;
    }
    public ActionType getActionType() {
        return actionType;
    }
    @Override
    public int observerType() {
        return ACTION;
    }
    @Override
    public void nodeEvent(BaseNode node, int reason) {
        if (reason == VALUE_UPD) {
            doAction(node);
        }
    }
}

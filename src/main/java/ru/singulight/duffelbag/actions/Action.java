package ru.singulight.duffelbag.actions;

import static ru.singulight.duffelbag.actions.ActionType.*;

/**
 * Created by Grigorii info@singulight.ru on 02.11.17.
 */
public abstract class Action {
    protected Integer actionId = 0;
    protected String description = "";
    protected ActionType actionType = JAVACLASS;
    abstract void doAction();

    public Integer getActionId() {
        return actionId;
    }
    public String getDescription() {
        return description;
    }
    public ActionType getActionType() {
        return actionType;
    }
}

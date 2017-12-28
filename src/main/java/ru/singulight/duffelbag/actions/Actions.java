package ru.singulight.duffelbag.actions;

import ru.singulight.duffelbag.actions.ActionType;

/**
 * Created by grigorii on 02.11.17.
 */
public interface Actions {
    void doAction();
    String getDescription();
    ActionType getActionType();
}

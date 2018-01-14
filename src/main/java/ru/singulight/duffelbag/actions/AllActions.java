package ru.singulight.duffelbag.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Grigorii info@singulight.ru on 13.01.18.
 */
public class AllActions {
    private static AllActions ourInstance = new AllActions();
    private AllActions() {
    }

    private static List<Action> allActions = new CopyOnWriteArrayList<>();

    public void insert(Action action) {
        allActions.add(action.getActionId(), action);
    }

    public void delete(int id) {
        allActions.remove(id);
    }

    public Action getById(Integer id) {
        return allActions.get(id);
    }

    public static AllActions getInstance() {
        return ourInstance;
    }
}

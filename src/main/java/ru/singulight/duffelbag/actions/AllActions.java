package ru.singulight.duffelbag.actions;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Grigorii info@singulight.ru on 13.01.18.
 */
public class AllActions {
    private static AllActions ourInstance = new AllActions();
    private AllActions() {
    }

    private static Map<Integer, Action> allActions = new Hashtable<>();

    public void insert(Action action) {
        allActions.put(action.getActionId(), action);
    }

    public void delete(int id) {
        allActions.remove(id);
    }

    public Action getById(Integer id) {
        return allActions.get(id);
    }

    public Map getAll () {
        return allActions;
    }

    public List<Action> getAllByList(){
        return new LinkedList<>(allActions.values());
    }

    public static AllActions getInstance() {
        return ourInstance;
    }
}

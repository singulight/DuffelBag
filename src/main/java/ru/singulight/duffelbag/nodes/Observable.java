package ru.singulight.duffelbag.nodes;

import ru.singulight.duffelbag.actions.Observer;

import java.util.List;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 13.05.17
 */
public interface Observable {
    void registerUpdateObserver(Observer o);
    void removeUpdateObserver(Observer o);
    void notifyObservers();
    List<Integer> getObserversIds();
}

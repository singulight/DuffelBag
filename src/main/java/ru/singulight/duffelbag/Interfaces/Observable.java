package ru.singulight.duffelbag.Interfaces;

import java.util.List;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 13.05.17
 */
public interface Observable {
    void registerUpdateObserver(UpdateObserver o);
    void removeUpdateObserver(UpdateObserver o);
    void notifyObservers();
    List<Integer> getObserversIds();
}

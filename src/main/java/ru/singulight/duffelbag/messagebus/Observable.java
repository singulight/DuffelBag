package ru.singulight.duffelbag.messagebus;

import java.util.List;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 13.05.17
 */
public interface Observable {
    void registerUpdateObserver(UpdateValueObserver o);
    void removeUpdateObserver(UpdateValueObserver o);
    void notifyObservers();
    List<Integer> getObserversIds();
}

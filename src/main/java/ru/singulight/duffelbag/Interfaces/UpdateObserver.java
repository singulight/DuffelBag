package ru.singulight.duffelbag.Interfaces;

import ru.singulight.duffelbag.nodes.BaseNode;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 13.05.17
 */
public interface UpdateObserver {
    void updateChanges(BaseNode observable);
    Integer getId();
}

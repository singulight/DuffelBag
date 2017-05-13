package ru.singulight.duffelbag.actions;

import ru.singulight.duffelbag.nodes.BaseNode;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 13.05.17
 */
public interface Observer {
    void update (BaseNode observable);
    Integer getId();
}

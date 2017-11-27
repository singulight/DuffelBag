package ru.singulight.duffelbag.Interfaces;

import ru.singulight.duffelbag.actions.ActionType;
import ru.singulight.duffelbag.nodes.BaseNode;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 13.05.17
 */
public interface UpdateValueObserver {
    void updateNodeValueEvent(BaseNode observable);
    Integer getId();
}

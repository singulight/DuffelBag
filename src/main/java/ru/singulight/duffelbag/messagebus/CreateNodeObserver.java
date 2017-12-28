package ru.singulight.duffelbag.messagebus;

import ru.singulight.duffelbag.nodes.BaseNode;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 22.05.17
 */
public interface CreateNodeObserver {
    public void createNodeEvent(BaseNode observable);
}

package ru.singulight.duffelbag.messagebus;

import ru.singulight.duffelbag.nodes.BaseNode;

/**
 * Created by grigorii on 12.12.17.
 */
public interface NodeEventObserver {
    public void nodeEvent(BaseNode node, int reason);
    public int observerType();
}

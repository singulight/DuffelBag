package ru.singulight.duffelbag.messagebus;

import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;

import java.util.*;

/**
 * Created by grigorii on 02.12.17.
 */
public class MessageBus {
    private static MessageBus ourInstance = new MessageBus();

    public static MessageBus getInstance() {
        return ourInstance;
    }

    private AllNodes allNodes = AllNodes.getInstance();

    private MessageBus() {
    }

    /** Event type */
    public final static int CREATE = 0, UPDATE = 1;
    /** Observer type */
    public final static int ACTION = 16, DAO = 17, WEB = 18;
    /** Main array */
    private static Map<Long, List<NodeEventObserver>> members = new HashMap<>();

    public void registerObserver(Long observableId, NodeEventObserver observer) {
        if (!members.containsKey(observableId)) {
            List<NodeEventObserver> obs = new LinkedList<>();
            obs.add(observer);
            members.put(observableId,obs);
        } else {
            members.get(observableId).add(observer);
        }

    }

    public void unregisterObserver(Long observableId, NodeEventObserver observer) {
        List<NodeEventObserver> obs = members.get(observableId);
        if(obs != null) {
            obs.remove(observer);
            if (obs.isEmpty()) {
                members.remove(observableId);
            }
        }
    }

    public List<NodeEventObserver> getObserversForObservable(Long observableId) {
        return members.get(observableId);
    }

    public void onCreateEvent(BaseNode observable) {
        members.get(observable.getId()).forEach((NodeEventObserver o) -> {
            switch (o.observerType()) {
                
            }
            o.nodeEvent(observable, CREATE);
        });
    }

    public void onUpdateEvent(BaseNode observable) {
        members.get(observable.getId()).forEach((NodeEventObserver o) -> {
            o.nodeEvent(observable, UPDATE);
        });
    }

    private void doEventStrategy() {

    }

}
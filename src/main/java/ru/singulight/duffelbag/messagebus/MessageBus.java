package ru.singulight.duffelbag.messagebus;

import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

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
    public final static int CREATE = 0, VALUE_UPD = 1, CONFIG_UPD = 3 ,DEL = 4;
    /** Observer type */
    public final static int ACTION = 16, DAO = 17, WEB = 18;
    /** Observable id for all nodes*/
    public final static Long ALL_OBSERVABLES = 0L;
    /** Main Map <Observable, List of Observers>*/
    private static Map<Long, List<NodeEventObserver>> members = new Hashtable<>();
    static {
        members.put(0L,new LinkedList<>());
    }
    /**
     * @param observableId - observable node id, ALL_OBSERVABLES - on create event notify all
     * @param observer - observer interface*/
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
            if (obs.isEmpty() & !observableId.equals(ALL_OBSERVABLES)) {
                members.remove(observableId);
            }
        }
    }
    public List<NodeEventObserver> getObserversForObservable(Long observableId) {
        return members.get(observableId);
    }

    public List<Long> getObservablesForObserver(NodeEventObserver observer) {
        List<Long> observables = new LinkedList<>();
        members.forEach((k,v) -> {
            v.forEach(l -> {
                if (l.equals(observer)) observables.add(k);
            });
        });
        return observables;
    }

    public void onCreateEvent(BaseNode observable) {
        List<NodeEventObserver> member = members.get(observable.getId());
        if (member != null) member.forEach((NodeEventObserver o) -> {
            o.nodeEvent(observable, CREATE);
        });
        members.get(ALL_OBSERVABLES).forEach((NodeEventObserver o) -> o.nodeEvent(observable, CREATE));
    }

    public void onDeleteEvent(BaseNode observable) {
        List<NodeEventObserver> member = members.get(observable.getId());
        if (member != null) {
            member.forEach((NodeEventObserver o) -> {
                o.nodeEvent(observable, DEL);
            });
            members.get(ALL_OBSERVABLES).forEach((NodeEventObserver o) -> o.nodeEvent(observable, DEL));
            members.remove(observable.getId());
        }
    }

    public void onUpdateEvent(BaseNode observable) {
        List<NodeEventObserver> member = members.get(observable.getId());
        if (member != null) {
            member.forEach((NodeEventObserver o) -> {
                o.nodeEvent(observable, VALUE_UPD);
            });
        }
        members.get(ALL_OBSERVABLES).forEach((NodeEventObserver o) -> o.nodeEvent(observable, VALUE_UPD));
    }

    public void onConfigUpdateEvent(BaseNode observable) {
        List<NodeEventObserver> member = members.get(observable.getId());
        if (member != null) {
            member.forEach((NodeEventObserver o) -> {
                o.nodeEvent(observable, CONFIG_UPD);
            });
        }
        members.get(ALL_OBSERVABLES).forEach((NodeEventObserver o) -> o.nodeEvent(observable, CONFIG_UPD));
    }

    private void doEventStrategy() {

    }

}
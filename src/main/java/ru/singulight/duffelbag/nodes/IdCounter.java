package ru.singulight.duffelbag.nodes;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 04.08.16.
 * Device ID checker
 */
public class IdCounter {
    private static IdCounter ourInstance = new IdCounter();

    public static IdCounter getInstance() {
        return ourInstance;
    }

    private IdCounter() {
    }

    private static Long lastId = 0x400000000000000L;
    private static List<Long> knownIds = new LinkedList<>();
    private static List<Long> knownDbIds = new LinkedList<>();

    public Long getNewId () throws Exception {
        lastId++;
        if(knownIds.contains(lastId)) {
            lastId++;
            if (lastId > 0x5fffffffffffffffL) throw new Exception("IdCounter: New ID too big");
        }
        knownIds.add(lastId);
        return lastId;
    }
    public Long checkDbId (Long id) throws Exception {
        Long newId;
        if (knownDbIds.contains(id)) {
            newId = getNewId();
        } else {
            newId = id;
            knownDbIds.add(id);
        }
        return newId;
    }
}

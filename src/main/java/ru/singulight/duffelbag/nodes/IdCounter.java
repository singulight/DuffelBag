package ru.singulight.duffelbag.nodes;


import org.apache.log4j.Logger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 04.08.16.
 * Device ID checker
 */
public class IdCounter {
    private static IdCounter ourInstance = new IdCounter();
    private static final Logger log = Logger.getLogger(IdCounter.class);

    public static IdCounter getInstance() {
        return ourInstance;
    }

    private IdCounter() {
    }

    private static Long lastId = 0x400000000000000L;
    private static List<Long> knownIds = new LinkedList<>();
    private static List<Long> knownDbIds = new LinkedList<>();

    public Long getNewId () {
        lastId++;
        // TODO: make search binary tree
        if(knownIds.contains(lastId)) {
            lastId++;
            if (lastId > 0x5fffffffffffffffL) try {
                throw new Exception();
            } catch (Exception e) {
                log.error("IdCounter: New ID too big");
            }
        }
        knownIds.add(lastId);
        return lastId;
    }
    /**
     * @return id for duffelbag nodes*/
    public Long checkDbId (Long id) {
        Long newId;
        if (knownDbIds.contains(id)) {
            newId = getNewId();
        } else {
            newId = id;
            knownDbIds.add(id);
        }
        return newId;
    }
    public void deleteId(Long id) {
        knownDbIds.remove(id);
        knownIds.remove(id);
    }
}

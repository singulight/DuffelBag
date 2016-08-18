package ru.singulight.duffelbag.dao;

import ru.singulight.duffelbag.nodes.SensorNode;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 11.07.16.
 */
public interface NodeDao {
    public void mergeAllSensors();
    public void saveAllSensors();
    public void getSensor(String topic);
    public SensorNode saveSensor();
}

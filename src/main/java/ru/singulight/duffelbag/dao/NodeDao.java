package ru.singulight.duffelbag.dao;

import ru.singulight.duffelbag.actions.LuaScriptAction;
import ru.singulight.duffelbag.nodes.ActuatorNode;
import ru.singulight.duffelbag.nodes.Node;
import ru.singulight.duffelbag.nodes.SensorNode;
import ru.singulight.duffelbag.nodes.Thing;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 11.07.16.
 *
 */
public interface NodeDao {
    void mergeAllNodes();
    void saveAllNodes();

    void saveSensor(SensorNode sensorNode);
    void saveActuator(ActuatorNode actuatorNode);
    void saveThing(Thing thing);

    void saveValue(Node node);

    void saveLuaScript(LuaScriptAction action);
    LuaScriptAction getLuaScript (Long id);

}

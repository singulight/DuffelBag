package ru.singulight.duffelbag.dao;

import ru.singulight.duffelbag.actions.LuaScriptAction;
import ru.singulight.duffelbag.nodes.BaseNode;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 11.07.16.
 *
 */
public interface NodeDao {
    void mergeAllNodes();
    void saveAllNodes();

    void saveNode(BaseNode baseNode);

    void saveValue(BaseNode node);

    void saveLuaScript(LuaScriptAction action);
    LuaScriptAction getLuaScript (Long id);

}

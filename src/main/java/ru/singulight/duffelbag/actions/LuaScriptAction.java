package ru.singulight.duffelbag.actions;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;
import ru.singulight.duffelbag.messagebus.UpdateValueObserver;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.nodes.types.NodeType;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 29.12.15.
 */
public class LuaScriptAction extends Action implements UpdateValueObserver {

    private static final Logger log = Logger.getLogger(LuaScriptAction.class);
    private ScriptEngine lua = new ScriptEngineManager().getEngineByName("luaj");

    private Integer id;
    private String name;
    private String script;

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void doAction(BaseNode node) {
        BaseNode asd = new BaseNode(10,"duffelbag/voltage/", NodeType.VOLTAGE);
        script = "asd:setValue(1);";
        try {
            lua.put("asd",asd);
            lua.eval(script);
            //asd = (SensorNode) lua.get("gov");
            log.info(asd.getValue());
        } catch (Exception e) {
            log.error("Lua script error: ",e);
        }
    }

    @Override
    public Integer getActionId() {
        return null;
    }


    @Override
    public String getDescription() {
        return null;
    }


    @Override
    public ActionType getActionType() {
        return ActionType.LUASCRIPT;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void updateNodeValueEvent(BaseNode observable) {
        doAction(observable);
    }
}

package ru.singulight.duffelbag.actions;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Observable;
import org.apache.log4j.Logger;
import ru.singulight.duffelbag.nodes.SensorNode;
import ru.singulight.duffelbag.nodes.types.NodeType;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 29.12.15.
 */
public class LuaScriptSensorAction implements Actionable {

    private static final Logger log = Logger.getLogger(LuaScriptSensorAction.class);
    private ScriptEngine lua = new ScriptEngineManager().getEngineByName("luaj");

    private String script;

    @Override
    public void update(Observable o, Object arg) {
        SensorNode asd = new SensorNode(10,"duffelbag/voltage/", NodeType.VOLTAGE);
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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
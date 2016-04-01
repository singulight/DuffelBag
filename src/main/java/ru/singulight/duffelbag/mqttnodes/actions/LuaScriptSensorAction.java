package ru.singulight.duffelbag.mqttnodes.actions;

import org.luaj.vm2.lib.jse.JseMathLib;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Observable;
import org.apache.log4j.Logger;
import ru.singulight.duffelbag.mqttnodes.SensorNode;
import ru.singulight.duffelbag.mqttnodes.types.NodeType;

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
        script = "amm = asd:getId();";
        try {
            lua.put("asd",asd);
            lua.eval(script);
            log.info("y= "+lua.get("amm"));
        } catch (Exception e) {
            log.error("Lua script error: "+e.getMessage());
        }
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}

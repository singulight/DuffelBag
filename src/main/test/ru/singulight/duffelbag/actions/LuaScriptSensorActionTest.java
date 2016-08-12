package ru.singulight.duffelbag.actions;

import org.junit.Test;

import java.util.Observable;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 01.04.16.
 */
public class LuaScriptSensorActionTest {

    @Test
    public void testUpdate() throws Exception {
        LuaScriptSensorAction action = new LuaScriptSensorAction();
        action.update(new Observable(), "d");
    }
}
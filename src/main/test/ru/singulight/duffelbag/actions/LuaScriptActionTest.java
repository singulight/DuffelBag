package ru.singulight.duffelbag.actions;

import org.junit.Test;

import java.util.Observable;

/**
 * Created by Grigorii Nizovoi info@singulight.ru on 01.04.16.
 */
public class LuaScriptActionTest {

    @Test
    public void testUpdate() throws Exception {
        LuaScriptAction action = new LuaScriptAction();
        action.update(new Observable(), "d");
    }
}
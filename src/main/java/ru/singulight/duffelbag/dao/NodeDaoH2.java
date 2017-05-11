package ru.singulight.duffelbag.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.singulight.duffelbag.actions.LuaScriptAction;
import ru.singulight.duffelbag.nodes.BaseNode;

import java.util.Date;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 21.07.16.
 */
public class NodeDaoH2 implements NodeDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void mergeAllNodes() {

    }

    @Override
    public void saveAllNodes() {

    }

    @Override
    public void saveNode(BaseNode baseNode) {

    }


    @Override
    public void saveValue(BaseNode node) {

    }

    @Override
    public void saveLuaScript(LuaScriptAction action) {

    }

    @Override
    public LuaScriptAction getLuaScript(Long id) {
        return null;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}

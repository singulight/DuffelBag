package ru.singulight.duffelbag.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.singulight.duffelbag.messagebus.MessageBus;
import ru.singulight.duffelbag.messagebus.NodeEventObserver;
import ru.singulight.duffelbag.actions.LuaScriptAction;
import ru.singulight.duffelbag.nodes.AllNodes;
import ru.singulight.duffelbag.nodes.BaseNode;
import ru.singulight.duffelbag.nodes.types.NodePurpose;
import ru.singulight.duffelbag.nodes.types.NodeType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ru.singulight.duffelbag.messagebus.MessageBus.*;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 21.07.16.
 */
public class NodeDaoH2 implements NodeDao, NodeEventObserver {

    private JdbcTemplate jdbcTemplate;
    private AllNodes allNodes = AllNodes.getInstance();
    private MessageBus messageBus = MessageBus.getInstance();

    NodeDaoH2() {}
    {messageBus.registerObserver(ALL_OBSERVABLES, this);}
    @Override
    public void mergeAllNodes() {

    }

    @Override
    public void saveAllNodes() {
        jdbcTemplate.execute("");
    }

    @Override
    public void loadAllNodes() {
        List<BaseNode> nodes = jdbcTemplate.query("SELECT * FROM NODE", new NodeMapper());
        nodes.forEach((BaseNode n)-> {
            allNodes.insert(n);
        });
    }

    @Override
    public void saveNode(BaseNode baseNode) {
        jdbcTemplate.update("DELETE FROM NODE WHERE ID=?", baseNode.getId());
        jdbcTemplate.update("INSERT INTO NODE (ID, NAME, TOPIC, VERSION, KNOWN, NODETYPE, NODEPURPOSE, CONFIGMESSAGE) " +
                "VALUES (?,?,?,?,?,?,?,?)", baseNode.getId(), baseNode.getName(), baseNode.getMqttTopic(), baseNode.getVersion(),
                baseNode.isKnown(), baseNode.getNodeType().toString(), baseNode.getPurpose().toString(), baseNode.getValue());
    }

    @Override
    public void updateConfiguration(BaseNode baseNode) {
        jdbcTemplate.update("");
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

    @Override
    public void nodeEvent(BaseNode observable, int reason) {
        switch (reason) {
            case CREATE:
                saveNode(observable);
                break;
            case VALUE_UPD:
                saveValue(observable);
                break;
            case CONFIG_UPD:
                break;
            case DEL:
                break;
        }
    }
    @Override
    public int observerType() {
        return DAO;
    }

    private static final class NodeMapper implements RowMapper<BaseNode> {
        @Override
        public BaseNode mapRow(ResultSet rs, int i) throws SQLException {
            BaseNode node = new BaseNode();
            node.setId(rs.getLong("id"));
            node.setName(rs.getString("name"));
            node.setMqttTopic(rs.getString("topic"));
            node.setVersion(rs.getString("version"));
            node.setKnown(rs.getBoolean("known"));
            node.setNodeType(NodeType.valueOf(rs.getString("nodeType")));
            node.setPurpose(NodePurpose.valueOf(rs.getString("nodePurpose")));
            return node;
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}

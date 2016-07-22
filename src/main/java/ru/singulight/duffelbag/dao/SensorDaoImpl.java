package ru.singulight.duffelbag.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.singulight.duffelbag.nodes.SensorNode;

import java.sql.SQLException;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 21.07.16.
 */
public class SensorDaoImpl implements SensorDao {


    private JdbcTemplate jdbcTemplate;

    @Override
    public void mergeAllSensors() {

    }

    @Override
    public void saveAllSensors() {

    }

    @Override
    public void getSensor(String topic) {
        String query = "SELECT * FROM sensors WHERE topic = ?";
        SensorNode sensor = new SensorNode();


    }

    @Override
    public SensorNode saveSensor() {
        return null;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

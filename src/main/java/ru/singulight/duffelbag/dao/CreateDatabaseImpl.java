package ru.singulight.duffelbag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 21.07.16.
 */
public class CreateDatabaseImpl implements CreateDatabase {
    private JdbcTemplate jdbcTemplate;

    public CreateDatabaseImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS node (" +
                "id IDENTITY, " +
                "name TEXT," +
                "topic VARCHAR(255) PRIMARY KEY, " +
                "version VARCHAR(16), " +
                "known BOOLEAN, " +
                "nodetype INT," +
                "minValue REAL," +
                "maxValue REAL," +
                "thingId BIGINT," +
                "configMessage TEXT," +
                "location VARCHAR(255));"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS nodeValues (" +
                "id IDENTITY," +
                "nodeId BIGINT," +
                "time TIMESTAMP," +
                "value REAL," +
                "textValue TEXT)"
        );
    }
}

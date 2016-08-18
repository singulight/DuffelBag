package ru.singulight.duffelbag.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 21.07.16.
 */
public class CreateDatabaseH2 implements CreateDatabase {

    private JdbcTemplate jdbcTemplate;

    public CreateDatabaseH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS node (" +
                        "id BIGINT PRIMARY KEY, " +
                        "name TEXT," +
                        "topic VARCHAR(255) UNIQUE, " +
                        "version VARCHAR(16), " +
                        "known BOOLEAN, " +
                        "nodetype INT," +
                        "minValue REAL," +
                        "maxValue REAL," +
                        "idThing BIGINT," +
                        "configMessage TEXT," +
                        "location VARCHAR(255));"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS nodeValues (" +
                        "id IDENTITY," +
                        "idNode BIGINT," +
                        "time TIMESTAMP," +
                        "value REAL," +
                        "textValue TEXT)"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS luaSripts (" +
                        "id IDENTITY," +
                        "script TEXT) "
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS observers (" +
                         "idScript IDENTITY," +
                         "idSensor BIGINT)"
        );
    }
}

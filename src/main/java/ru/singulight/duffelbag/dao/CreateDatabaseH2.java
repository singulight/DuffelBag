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
    public void create10() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS node (" +
                        "id BIGINT PRIMARY KEY, " +
                        "name TEXT," +
                        "topic VARCHAR(255) UNIQUE, " +
                        "version VARCHAR(16), " +
                        "known BOOLEAN, " +
                        "nodetype VARCHAR(255)," +
                        "nodepurpose VARCHAR(32)," +
                        "configMessage TEXT);"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS nodeOptions (" +
                        "id IDENTITY," +
                        "idNode BIGINT," +
                        "opKey VARCHAR(255)," +
                        "opValue TEXT)"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS nodeValues (" +
                        "id IDENTITY," +
                        "idNode BIGINT," +
                        "time TIMESTAMP," +
                        "nodeValue TEXT," +
                        "rawValue BLOB)"
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS luaSriptsActions (" +
                        "id INTEGER PRIMARY KEY," +
                        "description TEXT," +
                        "textScript TEXT) "
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS javaClassActions (" +
                        "id INTEGER PRIMARY KEY," +
                        "description TEXT," +
                        "className VARCHAR(255)) "
        );
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS observers (" +
                         "idAction IDENTITY," +
                         "idNode BIGINT)"
        );
    }
}

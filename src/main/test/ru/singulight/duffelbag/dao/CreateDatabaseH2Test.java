package ru.singulight.duffelbag.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Grigorii Nizovoy info@singulight.ru on 18.08.16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateDatabaseH2Test {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    public void testCreate() throws Exception {
        CreateDatabase cdb = (CreateDatabase) ctx.getBean("createDatabase");
        cdb.create();
    }
}
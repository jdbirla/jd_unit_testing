package com.jd.springboot.intergration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Created by jd birla on 27-11-2022 at 06:56
 */
public abstract class AbstractionBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;
    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest").withDatabaseName("empdb").withUsername("empuser").withPassword("emppass");
        MY_SQL_CONTAINER.start();
    }


    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry)
    {
        dynamicPropertyRegistry.add("spring.datasource.url",MY_SQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username",MY_SQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",MY_SQL_CONTAINER::getPassword);

    }
}

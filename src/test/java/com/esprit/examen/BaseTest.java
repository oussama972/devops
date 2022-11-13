package com.esprit.examen;



import org.testcontainers.containers.MySQLContainer;

public abstract class BaseTest {

    static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("achet")
            .withUsername("iheb")
            .withPassword("iheb")
            .withReuse(true);

    static {
        mySQLContainer.start();
    }
}

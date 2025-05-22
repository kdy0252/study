package com.dykim.study.support.base;

import com.dykim.study.support.container.SharedPostgresTestConfig;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.annotation.DirtiesContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class IsolatedDatabaseTest {

    protected String dbName;

    @BeforeAll
    void setupDb() {
        String className = getClass().getSimpleName().toLowerCase();
        String uuidSuffix = UUID.randomUUID().toString().replace("-", "");
        dbName = "testdb_" + className + "_" + uuidSuffix;
        SharedPostgresTestConfig.createDatabase(dbName);

        System.setProperty("spring.datasource.url", SharedPostgresTestConfig.getJdbcUrl(dbName));
        System.setProperty("spring.datasource.username", SharedPostgresTestConfig.getInstance().getUsername());
        System.setProperty("spring.datasource.password", SharedPostgresTestConfig.getInstance().getPassword());
    }

    @AfterAll
    void cleanupDb() {
        SharedPostgresTestConfig.dropDatabase(dbName);
    }
}
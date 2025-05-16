package com.dykim.study.support.base;

import com.dykim.study.support.container.SharedPostgresTestConfig;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IsolatedDatabaseTest {
    protected static String dbName;

    static {
        dbName = "testdb_" + UUID.randomUUID().toString().replace("-", "_");
        SharedPostgresTestConfig.createDatabase(dbName);
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        PostgreSQLContainer<?> container = SharedPostgresTestConfig.getInstance();
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.url", () ->
            container.getJdbcUrl().replace("/template1", "/" + dbName));
    }

    @AfterAll
    void cleanup() {
        SharedPostgresTestConfig.dropDatabase(dbName);
    }
}

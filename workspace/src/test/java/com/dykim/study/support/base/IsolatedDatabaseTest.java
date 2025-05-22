package com.dykim.study.support.base;

import com.dykim.study.support.container.SharedPostgresTestConfig;
import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class IsolatedDatabaseTest {

    @Getter
    private static final ThreadLocal<String> dbNameTL = new ThreadLocal<>();

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", () -> SharedPostgresTestConfig.getInstance().getUsername());
        registry.add("spring.datasource.password", () -> SharedPostgresTestConfig.getInstance().getPassword());
        registry.add("spring.datasource.url", () -> SharedPostgresTestConfig.getJdbcUrl(dbNameTL.get()));
    }

    @BeforeAll
    void setupDb() {
        String className = getClass().getSimpleName().toLowerCase();
        String uuidSuffix = UUID.randomUUID().toString().replace("-", "");
        String dbName = "testdb_" + className + "_" + uuidSuffix;
        dbNameTL.set(dbName);
        SharedPostgresTestConfig.createDatabase(dbName);
    }

    @AfterAll
    void cleanupDb() {
        SharedPostgresTestConfig.dropDatabase(dbNameTL.get());
        dbNameTL.remove();
    }
}
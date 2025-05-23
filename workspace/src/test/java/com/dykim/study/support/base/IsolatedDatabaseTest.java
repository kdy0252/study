package com.dykim.study.support.base;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.events.core.EventPublicationRegistry;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class IsolatedDatabaseTest {
    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
        .withDatabaseName("test")
        .withUsername("postgres")
        .withPassword("secret")
        .withReuse(true);
    @Autowired
    private EventPublicationRegistry eventPublicationRegistry;

    @Autowired
    private DataSource dataSource;

    protected static void initSchema(DynamicPropertyRegistry registry) {
        postgres.start();

        // hyphen 제거한 UUID 사용
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String schema = "schema_" + uuid;

        try (Connection conn = DriverManager.getConnection(
            postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
            Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE SCHEMA " + schema);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info(">>>> Using schema: {}", schema);

        registry.add("spring.datasource.url",
            () -> postgres.getJdbcUrl() + "&currentSchema=" + schema);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    /**
     * 테스트 시작 전에 이벤트 레지스트리만 파기해서
     * shutdown hook 단계의 DB 쿼리를 없앤다.
     */
    @BeforeAll
    void disableEventRegistryShutdown() throws Exception {
        if (eventPublicationRegistry instanceof DisposableBean) {
            ((DisposableBean) eventPublicationRegistry).destroy();
        }
    }

    /**
     * 모든 테스트가 끝난 뒤(@AfterAll) Hikari 풀을 닫아서
     * Spring shutdown hook 에서 별도의 커넥션 검증을 하지 않도록 한다.
     */
    @AfterAll
    void closeHikariPool() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }

}
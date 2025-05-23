package com.dykim.study.example;

import com.dykim.study.support.base.IsolatedDatabaseTest;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Slf4j
@SpringBootTest
class IsolatedDatabaseExampleTest2 extends IsolatedDatabaseTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private Environment env;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry r) {
        initSchema(r);  // ← 딱 한 줄만!
    }

    @Test
    void printCurrentSchema() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            // JDBC 4.1 이상이면 getSchema() 사용 가능
            String currentSchema = conn.getSchema();
            log.info("Current schema in this test: {}", currentSchema);
        }
    }

    @Test
    void printDatasourceUrl() {
        String url = env.getProperty("spring.datasource.url");
        log.info("Datasource URL: {}", url);
    }

}

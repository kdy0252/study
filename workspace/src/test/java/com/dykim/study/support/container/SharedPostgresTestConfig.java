package com.dykim.study.support.container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class SharedPostgresTestConfig {
    private static final DockerImageName IMAGE = DockerImageName.parse("postgres:15.3");
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
        new PostgreSQLContainer<>(IMAGE)
            .withDatabaseName("template-dummy") // template1 사용 방지
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    static {
        POSTGRES_CONTAINER.start();
    }

    public static PostgreSQLContainer<?> getInstance() {
        return POSTGRES_CONTAINER;
    }

    public static void createDatabase(String dbName) {
        try (Connection conn = DriverManager.getConnection(
            POSTGRES_CONTAINER.getJdbcUrl(),
            POSTGRES_CONTAINER.getUsername(),
            POSTGRES_CONTAINER.getPassword());
            Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE " + dbName + " TEMPLATE postgres");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create DB " + dbName, e);
        }
    }

    public static void dropDatabase(String dbName) {
        try (Connection conn = DriverManager.getConnection(
            POSTGRES_CONTAINER.getJdbcUrl(),
            POSTGRES_CONTAINER.getUsername(),
            POSTGRES_CONTAINER.getPassword());
            Statement stmt = conn.createStatement()) {

            // 종료 전에 연결된 세션을 모두 끊어야 drop 가능
            stmt.execute("SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '" + dbName + "'");
            stmt.execute("DROP DATABASE IF EXISTS " + dbName);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop DB " + dbName, e);
        }
    }

    public static String getJdbcUrl(String dbName) {
        return POSTGRES_CONTAINER.getJdbcUrl().replace("/template1", "/" + dbName);
    }
}
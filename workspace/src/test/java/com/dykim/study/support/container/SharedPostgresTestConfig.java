package com.dykim.study.support.container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class SharedPostgresTestConfig {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16")
        .withUsername("user")
        .withPassword("pass")
        .withDatabaseName("template1") // placeholder
        .withReuse(true);

    static {
        container.start();
    }

    public static PostgreSQLContainer<?> getInstance() {
        return container;
    }

    public static void createDatabase(String dbName) {
        try (Connection conn = DriverManager.getConnection(
            container.getJdbcUrl(), container.getUsername(), container.getPassword()
        )) {
            conn.createStatement().execute("CREATE DATABASE " + dbName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropDatabase(String dbName) {
        try (Connection conn = DriverManager.getConnection(
            container.getJdbcUrl(), container.getUsername(), container.getPassword())) {

            // 연결 끊기
            conn.createStatement().execute(
                "SELECT pg_terminate_backend(pg_stat_activity.pid) " +
                    "FROM pg_stat_activity " +
                    "WHERE pg_stat_activity.datname = '" + dbName + "' " +
                    "AND pid <> pg_backend_pid()"
            );

            // DROP
            conn.createStatement().execute("DROP DATABASE IF EXISTS " + dbName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
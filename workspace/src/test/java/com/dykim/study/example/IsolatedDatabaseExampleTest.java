package com.dykim.study.example;

import com.dykim.study.support.base.IsolatedDatabaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class IsolatedDatabaseExampleTest extends IsolatedDatabaseTest {

    @Test
    void contextLoads() {
        log.info("db name: {}", getDbNameTL().get());
    }

}

package com.dykim.study.example;

import com.dykim.study.support.base.IsolatedDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class IsolatedDatabaseExampleTest extends IsolatedDatabaseTest {

    @Test
    void contextLoads() {
        System.out.println("Using db: " + dbName);
    }

}

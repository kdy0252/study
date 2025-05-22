package com.dykim.study.support.base;

import com.dykim.study.support.container.SharedRedisContainerConfig;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IsolatedRedisTest {

    private static final AtomicInteger NEXT_REDIS_DB = new AtomicInteger(0); // 0~15까지 사용

    protected static int redisDbIndex;

    @DynamicPropertySource
    static void registerRedisProps(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", SharedRedisContainerConfig::getHost);
        registry.add("spring.redis.port", SharedRedisContainerConfig::getPort);
        registry.add("spring.redis.database", () -> redisDbIndex);
    }

    @BeforeAll
    void allocateRedisDb() {
        redisDbIndex = NEXT_REDIS_DB.getAndIncrement();
        if (redisDbIndex > 15) {
            throw new IllegalStateException("Redis DB 번호 초과 (최대 16개까지 병렬 가능)");
        }
    }
}
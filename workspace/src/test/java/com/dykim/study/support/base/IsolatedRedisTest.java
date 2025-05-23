package com.dykim.study.support.base;

import com.dykim.study.support.config.TestRedisConfig;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Import(TestRedisConfig.class)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class IsolatedRedisTest {

    // JVM 당 한 번만 띄우는 Redis 컨테이너
    static final GenericContainer<?> REDIS =
        new GenericContainer<>(DockerImageName.parse("redis:7.0.5"))
            .withExposedPorts(6379)
            .withReuse(true);

    static {
        REDIS.start();
    }

    /** 하위 테스트 클래스에서 이 메서드를 한 줄만 호출하세요 */
    protected static void initRedis(DynamicPropertyRegistry registry) {
        String host = REDIS.getHost();
        int port = REDIS.getMappedPort(6379);
        // UUID 기반으로 충분히 고유한 프리픽스 생성
        String prefix = UUID.randomUUID().toString().substring(0, 8);

        // Spring 환경에 주입
        registry.add("spring.redis.host", () -> host);
        registry.add("spring.redis.port", () -> port);
        registry.add("test.redis.prefix", () -> prefix);
    }
}
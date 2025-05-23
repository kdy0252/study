package com.dykim.study.example;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.dykim.study.support.base.IsolatedRedisTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


@Slf4j
@SpringBootTest
public class IsolatedRedisExampleTest2 extends IsolatedRedisTest {
    // LettuceConnectionFactory를 직접 주입받아서 설정값을 조회
    @Autowired
    LettuceConnectionFactory connectionFactory;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry r) {
        initRedis(r);  // 한 줄로 init 호출
    }

    @Test
    void printDBInfo() {
        // 사용 중인 Redis 정보 출력
        log.info(
            "▶ Redis 연결 정보: host={}, port={}, dbIndex={}",
            connectionFactory.getHostName(),
            connectionFactory.getPort(),
            connectionFactory.getDatabase()
        );
    }

    @Test
    void testIsolatedRedisDb() {
        redisTemplate.opsForValue().set("foo", "bar");
        assertThat(redisTemplate.opsForValue().get("foo")).isEqualTo("bar");
    }
}

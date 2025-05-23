package com.dykim.study.support.config;

import java.util.Objects;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@TestConfiguration
public class TestRedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(Environment env) {
        String host = env.getProperty("spring.redis.host");
        int port = Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.redis.port")));
        assert host != null;
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(conf);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(
        LettuceConnectionFactory cf, Environment env) {

        String prefix = env.getProperty("test.redis.prefix", "");
        PrefixStringRedisSerializer keySer = new PrefixStringRedisSerializer(prefix);

        StringRedisTemplate tpl = new StringRedisTemplate(cf);
        tpl.setKeySerializer(keySer);
        tpl.setHashKeySerializer(keySer);
        tpl.afterPropertiesSet();
        return tpl;
    }

}
package com.dykim.study.support.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 키 직렬화 시점에 "{prefix}:{key}" 를 붙이고,
 * 역직렬화 시에는 ":{prefix}" 부분을 떼어냅니다.
 */
public class PrefixStringRedisSerializer implements RedisSerializer<String> {

    private final String prefix;
    private final StringRedisSerializer delegate = new StringRedisSerializer();

    public PrefixStringRedisSerializer(String prefix) {
        this.prefix = prefix.endsWith(":") ? prefix : prefix + ":";
    }

    @Override
    public byte[] serialize(String s) throws SerializationException {
        return delegate.serialize(prefix + s);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        String full = delegate.deserialize(bytes);
        if (full != null && full.startsWith(prefix)) {
            return full.substring(prefix.length());
        }
        return full;
    }
}

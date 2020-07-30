package com.lzn.mybatisplus.codegenerator.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis接口的实现
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public Boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public void setex(String key, int seconds, String value) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getbit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public Long bitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    @Override
    public void setrange(String key, long offset, String value) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        return redisTemplate.opsForValue().get(key, startOffset, endOffset);
    }

    @Override
    public Long decrBy(String key, long integer) {
        return redisTemplate.opsForValue().increment(key, -integer);
    }

    @Override
    public Long decr(String key) {
        return redisTemplate.opsForValue().increment(key, -1);
    }

    @Override
    public Long incrBy(String key, long integer) {
        return redisTemplate.opsForValue().increment(key, integer);
    }

    @Override
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public Long strlen(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    private Map<String, Double> tupleToMap(Set<ZSetOperations.TypedTuple<String>> tupleSet) {
        if (tupleSet == null) {
            return null;
        }
        Map<String, Double> map = new HashMap<>(tupleSet.size());
        tupleSet.forEach(item -> map.put(item.getValue(), item.getScore()));
        return map;
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long del(Set<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    @Override
    public Boolean expire(String key, int seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Boolean expireAt(String key, long unixTime) {
        return redisTemplate.expireAt(key, new Date(unixTime));
    }

    @Override
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public List<String> scan(String pattern, Integer count) {
        List<String> keys = new ArrayList<>(count);
        ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).count(count).build();
        redisTemplate.executeWithStickyConnection((connection) -> {
            Cursor<byte[]> cursor = connection.scan(scanOptions);
            cursor.forEachRemaining(item -> keys.add(new String(item, StandardCharsets.UTF_8)));
            return cursor;
        });
        return keys;
    }

}

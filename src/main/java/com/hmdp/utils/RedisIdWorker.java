package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class RedisIdWorker {

  private static final long BEGIN_TIMESTAMP = 1640995200L;
  private static final int COUNT_BITS = 32;
  private StringRedisTemplate stringRedisTemplate;

  public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  public long nextId(String keyPrefix) {
    // generate time stamp
    LocalDateTime now = LocalDateTime.now();
    long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
    long timestamp = nowSecond - BEGIN_TIMESTAMP;

    String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
    long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

    // got number

    // append

    return timestamp << COUNT_BITS | count;
  }

  public static void main(String[] args) {
    LocalDateTime time = LocalDateTime.of(2022, 1, 1, 0, 0);
    long second = time.toEpochSecond(ZoneOffset.UTC);
    System.out.println(second);
  }
}

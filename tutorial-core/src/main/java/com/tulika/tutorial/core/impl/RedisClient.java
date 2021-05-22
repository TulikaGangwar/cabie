package com.tulika.tutorial.core.impl;


import com.tulika.tutorial.core.BaseClient;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;
@Slf4j
public class RedisClient implements BaseClient {

    private final Jedis jedis;

    @Inject //no explicity call of this constructor and inject the depencies in the argument
    public RedisClient(final Jedis jedis) {

        this.jedis = jedis;
    }

    @Override
    public String put(String key, String value) {
        return jedis.set(key,value);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);

    }


}

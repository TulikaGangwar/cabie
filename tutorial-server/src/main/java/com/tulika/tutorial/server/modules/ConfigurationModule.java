package com.tulika.tutorial.server.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.tulika.tutorial.core.BaseClient;
import com.tulika.tutorial.core.impl.RedisClient;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.Jedis;

public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BaseClient.class).annotatedWith(Names.named("redis")).to(RedisClient.class);
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper(Environment environment) {
        return environment.getObjectMapper();
    }

    @Provides
    @Singleton // only one instance is allowed on run time
    public Jedis provideJedisClient(){
        return new Jedis();
    }

}

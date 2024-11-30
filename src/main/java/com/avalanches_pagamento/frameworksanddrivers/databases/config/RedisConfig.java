package com.avalanches_pagamento.frameworksanddrivers.databases.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private final Environment ambiente;

    public RedisConfig(Environment ambiente) {
        this.ambiente = ambiente;
        logger.info("Initializing RedisConfig");
    }

    @Bean
    public RedisClient redisClient() {
        String host = ambiente.getProperty("spring.data.redis.host");
        String port = ambiente.getProperty("spring.data.redis.port");
        String password = ambiente.getProperty("spring.data.redis.password");

        String redisUrl = String.format("redis://%s:%s", host, port);
        if (password != null && !password.isEmpty()) {
            redisUrl = String.format("redis://:%s@%s:%s", password, host, port);
        }

        logger.debug("Creating RedisClient with URL: {}", redisUrl);
        if (host == null || port == null) {
            logger.warn("Redis configuration is incomplete: host or port is null.");
        }
        return RedisClient.create(redisUrl);
    }

    @Bean
    public StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
        logger.info("Establishing StatefulRedisConnection");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        logger.info("StatefulRedisConnection established successfully");
        return connection;
    }

    @Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
        logger.info("Creating RedisCommands bean");
        return connection.sync();
    }
}

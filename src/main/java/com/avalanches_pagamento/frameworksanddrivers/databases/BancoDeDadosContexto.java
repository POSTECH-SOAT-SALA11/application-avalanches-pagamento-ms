package com.avalanches_pagamento.frameworksanddrivers.databases;

import com.avalanches_pagamento.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class BancoDeDadosContexto implements BancoDeDadosContextoInterface {

    private final RedisCommands<String, String> redisCommands;

    @Inject
    public BancoDeDadosContexto(RedisCommands<String, String> redisCommands) {
        this.redisCommands = redisCommands;
    }

    @Override
    public RedisCommands<String, String> getRedisCommands() {
        return redisCommands;
    }
}

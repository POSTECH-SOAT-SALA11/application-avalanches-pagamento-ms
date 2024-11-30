package com.avalanches_pagamento.frameworksanddrivers.databases.interfaces;

import io.lettuce.core.api.sync.RedisCommands;

public interface BancoDeDadosContextoInterface {

    RedisCommands<String, String> getRedisCommands();

}

package com.avalanches_pagamento.frameworksanddrivers.databases;

import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class BancoDeDadosContextoTest {

    @Mock
    private RedisCommands<String, String> redisCommandsMock;

    @InjectMocks
    private BancoDeDadosContexto bancoDeDadosContexto;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructorAndGetRedisCommands() {
        // Verify that the constructor correctly initializes the redisCommands field
        assertNotNull(bancoDeDadosContexto);
        assertEquals(redisCommandsMock, bancoDeDadosContexto.getRedisCommands());
    }

    @Test
    void testGetRedisCommands() {
        // Verify that getRedisCommands() returns the mocked RedisCommands instance
        RedisCommands<String, String> result = bancoDeDadosContexto.getRedisCommands();
        assertEquals(redisCommandsMock, result);
    }
}

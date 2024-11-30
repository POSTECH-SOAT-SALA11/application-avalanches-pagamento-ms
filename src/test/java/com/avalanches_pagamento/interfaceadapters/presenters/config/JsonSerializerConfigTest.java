package com.avalanches_pagamento.interfaceadapters.presenters.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JsonSerializerConfig.class)  // Explicitly provide the config class here
class JsonSerializerConfigTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObjectMapperIsNotNull() {
        // Assert that the ObjectMapper bean is not null
        assertNotNull(objectMapper, "ObjectMapper bean should be present in the application context.");
    }

    @Test
    void testJavaTimeModuleIsRegistered() {
        // Check if JavaTimeModule is registered by default for ObjectMapper
        Set<Object> registeredModules =  objectMapper.getRegisteredModuleIds();
        boolean isJavaTimeModulePresent = registeredModules.contains(new JavaTimeModule().getTypeId());

        // Assert that the JavaTimeModule is registered
        assertTrue(isJavaTimeModulePresent, "JavaTimeModule should be registered with ObjectMapper.");
    }
}

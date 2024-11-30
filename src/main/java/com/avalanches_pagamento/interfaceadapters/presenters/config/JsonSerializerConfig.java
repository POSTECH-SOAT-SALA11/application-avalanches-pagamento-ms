package com.avalanches_pagamento.interfaceadapters.presenters.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonSerializerConfig {

    @Bean
    protected ObjectMapper objectMapper() {
        final ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}

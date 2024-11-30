package com.avalanches_pagamento.interfaceadapters.presenters;

import com.avalanches_pagamento.frameworksanddrivers.databases.JsonProcessingCustomException;
import com.avalanches_pagamento.interfaceadapters.presenters.interfaces.JsonPresenterInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonPresenter implements JsonPresenterInterface {

    private final ObjectMapper objectMapper;

    public JsonPresenter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> String serialize(T object) throws JsonProcessingCustomException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new JsonProcessingCustomException(ex.getMessage());
        }
    }

    @Override
    public <T> T deserialize(String serializedObject, Class<T> targetClass) throws JsonProcessingCustomException {
        try {
            return objectMapper.readValue(serializedObject, targetClass);
        } catch (JsonProcessingException | NullPointerException ex) {
            throw new JsonProcessingCustomException(ex.getMessage());
        }
    }
}

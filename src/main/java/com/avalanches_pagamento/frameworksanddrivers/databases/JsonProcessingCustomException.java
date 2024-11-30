package com.avalanches_pagamento.frameworksanddrivers.databases;

public class JsonProcessingCustomException extends RuntimeException {
    public JsonProcessingCustomException(String message) {
        super("Erro ao processar JSON: " + message);
    }
}

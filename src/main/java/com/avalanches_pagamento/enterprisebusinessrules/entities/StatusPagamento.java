package com.avalanches_pagamento.enterprisebusinessrules.entities;

public enum StatusPagamento {

    APROVADO("Aprovado"),
    REPROVADO("Reprovado");

    private final String value;

    StatusPagamento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

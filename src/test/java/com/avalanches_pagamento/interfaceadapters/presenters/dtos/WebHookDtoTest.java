package com.avalanches_pagamento.interfaceadapters.presenters.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        boolean sucesso = true;
        String mensagem = "Success";

        WebHookDto dto = new WebHookDto(sucesso, mensagem);

        // Act & Assert
        assertEquals(sucesso, dto.sucesso());
        assertEquals(mensagem, dto.mensagem());
    }

    @Test
    void testEquality() {
        // Arrange
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(true, "Success");

        // Act & Assert
        assertEquals(dto1, dto2);
    }

    @Test
    void testEqualityWithDifferentData() {
        // Arrange
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(false, "Failure");

        // Act & Assert
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode() {
        // Arrange
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(true, "Success");

        // Act & Assert
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCodeWithDifferentData() {
        // Arrange
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(false, "Failure");

        // Act & Assert
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        WebHookDto dto = new WebHookDto(true, "Success");

        // Act
        String expectedToString = "WebHookDto[sucesso=true, mensagem=Success]";

        // Assert
        assertEquals(expectedToString, dto.toString());
    }
}

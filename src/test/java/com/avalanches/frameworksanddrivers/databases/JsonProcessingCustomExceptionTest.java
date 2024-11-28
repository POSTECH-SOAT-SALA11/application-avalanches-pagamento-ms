package com.avalanches.frameworksanddrivers.databases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JsonProcessingCustomExceptionTest {

    @Test
    public void testJsonProcessingCustomExceptionMessage() {
        // Arrange
        String errorMessage = "Invalid JSON format";

        // Act
        JsonProcessingCustomException exception = new JsonProcessingCustomException(errorMessage);

        // Assert
        assertEquals("Erro ao processar JSON: " + errorMessage, exception.getMessage());
    }

    @Test
    public void testJsonProcessingCustomExceptionType() {
        // Arrange
        String errorMessage = "Invalid JSON format";

        // Act
        JsonProcessingCustomException exception = new JsonProcessingCustomException(errorMessage);

        // Assert
        assertTrue(exception instanceof JsonProcessingCustomException);
    }

    @Test
    public void testJsonProcessingCustomExceptionIsRuntimeException() {
        // Arrange
        String errorMessage = "Invalid JSON format";

        // Act
        JsonProcessingCustomException exception = new JsonProcessingCustomException(errorMessage);

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}

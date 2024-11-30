package com.avalanches_pagamento.interfaceadapters.presenters;

import com.avalanches_pagamento.frameworksanddrivers.databases.JsonProcessingCustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JsonPresenterTest {

    private JsonPresenter jsonPresenter;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Mock the ObjectMapper
        objectMapper = Mockito.mock(ObjectMapper.class);
        jsonPresenter = new JsonPresenter(objectMapper);
    }

    @Test
    void testSerialize() throws JsonProcessingCustomException {
        // Arrange: Create a sample object
        String expectedJson = "{\"name\":\"John\",\"age\":30}";
        Person person = new Person("John", 30);

        // Mock ObjectMapper behavior
        try {
            when(objectMapper.writeValueAsString(person)).thenReturn(expectedJson);

            // Act: Call serialize method
            String json = jsonPresenter.serialize(person);

            // Assert: Verify that the serialized JSON is correct
            assertEquals(expectedJson, json);
        } catch (JsonProcessingException e) {
            fail("Exception should not be thrown here");
        }
    }

    @Test
    void testDeserialize() throws JsonProcessingCustomException {
        // Arrange: Prepare a JSON string
        String json = "{\"name\":\"John\",\"age\":30}";
        Person expectedPerson = new Person("John", 30);

        // Mock ObjectMapper behavior
        try {
            when(objectMapper.readValue(json, Person.class)).thenReturn(expectedPerson);

            // Act: Call deserialize method
            Person person = jsonPresenter.deserialize(json, Person.class);

            // Assert: Verify that the deserialized object matches the expected one
            assertEquals(expectedPerson, person);
        } catch (JsonProcessingException e) {
            fail("Exception should not be thrown here");
        }
    }

    @Test
    void testSerializeWithJsonProcessingException() {
        // Arrange: Create a sample object
        Person person = new Person("John", 30);

        // Mock ObjectMapper behavior to throw JsonProcessingException
        try {
            when(objectMapper.writeValueAsString(person)).thenThrow(JsonProcessingException.class);

            // Act & Assert: Expect JsonProcessingCustomException to be thrown
            JsonProcessingCustomException exception = assertThrows(JsonProcessingCustomException.class, () -> {
                jsonPresenter.serialize(person);
            });

            assertNotNull(exception.getMessage());
        } catch (JsonProcessingException e) {
            fail("Unexpected exception during mock setup");
        }
    }

    @Test
    void testDeserializeWithJsonProcessingException() {
        // Arrange: Prepare a JSON string
        String json = "{\"name\":\"John\",\"age\":30}";

        // Mock ObjectMapper behavior to throw JsonProcessingException
        try {
            when(objectMapper.readValue(json, Person.class)).thenThrow(JsonProcessingException.class);

            // Act & Assert: Expect JsonProcessingCustomException to be thrown
            JsonProcessingCustomException exception = assertThrows(JsonProcessingCustomException.class, () -> {
                jsonPresenter.deserialize(json, Person.class);
            });

            assertNotNull(exception.getMessage());
        } catch (JsonProcessingException e) {
            fail("Unexpected exception during mock setup");
        }
    }

    @Test
    void testDeserializeWithInvalidJson() {
        // Arrange: Prepare an invalid JSON string
        String invalidJson = "{\"name\":\"John\",\"age\":\"invalid\"}";  // age should be an integer

        try {
            when(objectMapper.readValue(invalidJson, Person.class)).thenCallRealMethod();
        } catch (JsonProcessingException e) {
            fail("Unexpected exception during mock setup");
        }

        // Act & Assert: Expect JsonProcessingCustomException to be thrown when deserialization fails
        JsonProcessingCustomException exception = assertThrows(JsonProcessingCustomException.class, () -> {
            jsonPresenter.deserialize(invalidJson, Person.class);
        });

        assertNotNull(exception.getMessage());
    }

    // Sample Person class for testing
    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return 31 * name.hashCode() + age;
        }
    }
}

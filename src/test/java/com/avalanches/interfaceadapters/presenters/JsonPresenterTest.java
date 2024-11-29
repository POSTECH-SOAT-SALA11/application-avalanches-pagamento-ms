package com.avalanches.interfaceadapters.presenters;

import com.avalanches.frameworksanddrivers.databases.JsonMappingCustomException;
import com.avalanches.frameworksanddrivers.databases.JsonProcessingCustomException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonPresenterTest {

    private JsonPresenter jsonPresenter;

    @BeforeEach
    void setUp() {
        jsonPresenter = new JsonPresenter();
    }

    @Test
    void testSerialize_Success() throws JsonProcessingCustomException, JsonMappingCustomException {
        // Arrange
        String expectedJson = "{\"name\":\"John\",\"age\":30}";
        Person person = new Person("John", 30);

        // Act
        String result = jsonPresenter.serialize(person);

        // Assert
        assertEquals(expectedJson, result);
    }

    @Test
    void testSerialize_ThrowsJsonMappingCustomException() {
        // Arrange
        Person person = new Person("John", 30);

        // Simulate a JsonMappingException during serialization
        JsonPresenter mockPresenter = mock(JsonPresenter.class);
        try {
            when(mockPresenter.serialize(person)).thenThrow(new JsonMappingCustomException("Mapping error"));
        } catch (JsonMappingCustomException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Act & Assert
        assertThrows(JsonMappingCustomException.class, () -> mockPresenter.serialize(person));
    }

    @Test
    void testSerialize_ThrowsJsonProcessingCustomException() {
        // Arrange
        Person person = new Person("John", 30);

        // Simulate a JsonMappingException during serialization
        JsonPresenter mockPresenter = mock(JsonPresenter.class);
        try {
            when(mockPresenter.serialize(person)).thenThrow(new JsonProcessingCustomException("Mapping error"));
        } catch (JsonProcessingCustomException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Act & Assert
        assertThrows(JsonProcessingCustomException.class, () -> mockPresenter.serialize(person));
    }

    @Test
    void testDeserialize_Success() throws JsonProcessingCustomException, JsonMappingCustomException {
        // Arrange
        String json = "{\"name\":\"John\",\"age\":30}";
        Person expectedPerson = new Person("John", 30);

        // Act
        Person result = jsonPresenter.deserialize(json, Person.class);

        // Assert
        assertEquals(expectedPerson.getName(), result.getName());
        assertEquals(expectedPerson.getAge(), result.getAge());
    }

    @Test
    void testDeserialize_ThrowsJsonMappingCustomException() {
        // Arrange
        String invalidJson = "{\"name\":\"John\",\"age\":\"invalid\"}";

        // Simulate a JsonMappingException during deserialization
        JsonPresenter mockPresenter = mock(JsonPresenter.class);
        try {
            when(mockPresenter.deserialize(invalidJson, Person.class)).thenThrow(new JsonMappingCustomException("Mapping error"));
        } catch (JsonProcessingCustomException | JsonMappingCustomException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // Act & Assert
        assertThrows(JsonMappingCustomException.class, () -> mockPresenter.deserialize(invalidJson, Person.class));
    }

    // Person class to be used in the test
    static class Person {
        private String name;
        private int age;

        @JsonCreator
        public Person(@JsonProperty("name") String name, @JsonProperty("age") int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}

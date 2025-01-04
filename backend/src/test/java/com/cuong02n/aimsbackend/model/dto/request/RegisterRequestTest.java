package com.cuong02n.aimsbackend.model.dto.request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("JohnDoe");
        request.setPassword("Password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidName() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("123InvalidName"); // Invalid name
        request.setPassword("Password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }
}

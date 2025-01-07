package test;

import com.cuong02n.aimsbackend.model.dto.request.RegisterRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Register Request Validation Tests")
public class RegisterRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should accept valid registration details")
    void testValidRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("JohnDoe");
        request.setPassword("Password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "No violations should be found for valid input");
    }

    @Test
    @DisplayName("Should reject invalid name format")
    void testInvalidName() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("123InvalidName");
        request.setPassword("Password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Violations should be found for invalid name");

        ConstraintViolation<RegisterRequest> violation = violations.iterator().next();
        assertEquals("name", violation.getPropertyPath().toString(),
                "Violation should be on name field");
    }

    @Test
    @DisplayName("Should reject invalid email format")
    void testInvalidEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("invalid-email");
        request.setName("JohnDoe");
        request.setPassword("Password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Violations should be found for invalid email");

        ConstraintViolation<RegisterRequest> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString(),
                "Violation should be on email field");
    }

    @Test
    @DisplayName("Should reject weak password")
    void testWeakPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setName("JohnDoe");
        request.setPassword("weak");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Violations should be found for weak password");

        ConstraintViolation<RegisterRequest> violation = violations.iterator().next();
        assertEquals("password", violation.getPropertyPath().toString(),
                "Violation should be on password field");
    }

    @Test
    @DisplayName("Should reject empty fields")
    void testEmptyFields() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("");
        request.setName("");
        request.setPassword("");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Violations should be found for empty fields");
        assertEquals(5, violations.size(), "Should have violations for all three empty fields");
    }
}
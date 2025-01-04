package test;


import com.cuong02n.aimsbackend.model.entity.Order;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderValidationTest {

    private Validator validator;
    private Order order;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Create a valid order for testing
        order = new Order();
        order.setAddress("123MainStreet");
        order.setPhone("0123456789");
        order.setProvince("Ha Noi");
    }

    // Phone Number Validation Tests
    @Test
    void phone_shouldBeValid_whenFormatIsCorrect() {
        // Test basic format
        order.setPhone("0123456789");
        assertTrue(isValid(order));

        // Test with dot separator
        order.setPhone("012.345.6789");
        assertTrue(isValid(order));

        // Test with hyphen separator
        order.setPhone("012-345-6789");
        assertTrue(isValid(order));

        // Test with slash separator
        order.setPhone("012/345/6789");
        assertTrue(isValid(order));
    }

    @Test
    void phone_shouldBeInvalid_whenEmpty() {
        order.setPhone("");
        assertValidationMessage(order, "Phone number is required");
    }

    @Test
    void phone_shouldBeInvalid_whenNull() {
        order.setPhone(null);
        assertValidationMessage(order, "Phone number is required");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1234567890",    // Doesn't start with 0
            "012345678",     // Too short
            "01234567890",   // Too long
            "0123-456.789",  // Mixed separators
            "0123.456-789",  // Mixed separators
            "abc0123456",    // Contains letters
            "01234.56789",   // Incorrect separator position
            "012-3456789"    // Incorrect separator position
    })
    void phone_shouldBeInvalid_withInvalidFormat(String phone) {
        order.setPhone(phone);
        assertFalse(isValid(order));
    }

    // Address Validation Tests
    @Test
    void address_shouldBeValid_whenFormatIsCorrect() {
        order.setAddress("123 Main Street");
        assertTrue(isValid(order));
    }

    @Test
    void address_shouldBeInvalid_whenEmpty() {
        order.setAddress("");
        assertValidationMessage(order, "Address is required");
    }

    @Test
    void address_shouldBeInvalid_whenNull() {
        order.setAddress(null);
        assertValidationMessage(order, "Address is required");
    }

    @Test
    void address_shouldBeInvalid_whenTooLong() {
        // Create string longer than 100 characters
        String longAddress = "a".repeat(101);
        order.setAddress(longAddress);
        assertValidationMessage(order, "Address must not exceed 100 characters");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123#MainStreet",   // Contains special character
            "123_MainStreet",   // Contains underscore
            "123@MainStreet"    // Contains @
    })
    void address_shouldBeInvalid_withInvalidCharacters(String address) {
        order.setAddress(address);
        assertFalse(isValid(order));
    }

    // Province Validation Tests
    @Test
    void province_shouldBeValid_whenNotEmpty() {
        order.setProvince("Ha Noi");
        assertTrue(isValid(order));
    }

    @Test
    void province_shouldBeInvalid_whenEmpty() {
        order.setProvince("");
        assertValidationMessage(order, "Province is required");
    }

    @Test
    void province_shouldBeInvalid_whenNull() {
        order.setProvince(null);
        assertValidationMessage(order, "Province is required");
    }

    // Helper Methods
    private boolean isValid(Order order) {
        return validator.validate(order).isEmpty();
    }

    private void assertValidationMessage(Order order, String expectedMessage) {
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(message -> message.equals(expectedMessage)));
    }
}
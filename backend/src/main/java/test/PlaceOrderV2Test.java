package test;

import com.cuong02n.aimsbackend.model.dto.request.PlaceOrderV2Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Place Order V2 Validation Tests")
public class PlaceOrderV2Test {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should accept valid order request")
    void testValidPlaceOrderV2Request() {
        PlaceOrderV2Request request = new PlaceOrderV2Request();
        request.setListProductRush(new HashSet<>());
        request.setListProductNoRush(new HashSet<>());
        request.setAddress("123 Main St");
        request.setPhone("0123456789");
        request.setProvince("Hanoi");
        request.setShippingInstruction("Leave at the door");
        request.setTimeInMinute(30);

        Set<ConstraintViolation<PlaceOrderV2Request>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "No violations should be found for valid input");
    }

    @Test
    @DisplayName("Should reject invalid time in minutes")
    void testInvalidTimeInMinute() {
        PlaceOrderV2Request request = new PlaceOrderV2Request();
        request.setListProductRush(new HashSet<>());
        request.setListProductNoRush(new HashSet<>());
        request.setAddress("123 Main St");
        request.setPhone("0123456789");
        request.setProvince("Hanoi");
        request.setShippingInstruction("Leave at the door");
        request.setTimeInMinute(-10); // Invalid time

        Set<ConstraintViolation<PlaceOrderV2Request>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Violations should be found for invalid time");

        ConstraintViolation<PlaceOrderV2Request> violation = violations.iterator().next();
        assertEquals("timeInMinute", violation.getPropertyPath().toString(),
                "Violation should be on timeInMinute field");
    }

    @Test
    @DisplayName("Should reject empty address")
    void testEmptyAddress() {
        PlaceOrderV2Request request = new PlaceOrderV2Request();
        request.setListProductRush(new HashSet<>());
        request.setListProductNoRush(new HashSet<>());
        request.setAddress(""); // Empty address
        request.setPhone("0123456789");
        request.setProvince("Hanoi");
        request.setShippingInstruction("Leave at the door");
        request.setTimeInMinute(30);

        Set<ConstraintViolation<PlaceOrderV2Request>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Violations should be found for empty address");

        ConstraintViolation<PlaceOrderV2Request> violation = violations.iterator().next();
        assertEquals("address", violation.getPropertyPath().toString(),
                "Violation should be on address field");
    }
}
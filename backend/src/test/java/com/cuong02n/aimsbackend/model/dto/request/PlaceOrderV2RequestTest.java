package com.cuong02n.aimsbackend.model.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceOrderV2RequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
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
        assertTrue(violations.isEmpty());
    }

    @Test
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
        assertEquals(0, violations.size());
        assertEquals("timeInMinute", violations.iterator().next().getPropertyPath().toString());
    }
}

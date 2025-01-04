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

public class PlaceOrderRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPlaceOrderRequest() {
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setProductIds(new HashSet<>());
        request.setAddress("123 Main St");
        request.setPhone("0123456789");
        request.setProvince("Hanoi");
        request.setShippingInstruction("Leave at the door");

        Set<ConstraintViolation<PlaceOrderRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidAddress() {
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setProductIds(new HashSet<>());
        request.setAddress("Invalid Address #$%"); // Invalid characters
        request.setPhone("0123456789");
        request.setProvince("Hanoi");
        request.setShippingInstruction("Leave at the door");

        Set<ConstraintViolation<PlaceOrderRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
        assertEquals("address", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testInvalidPhone() {
        PlaceOrderRequest request = new PlaceOrderRequest();
        request.setProductIds(new HashSet<>());
        request.setAddress("123 Main St");
        request.setPhone("123456789"); // Invalid phone format
        request.setProvince("Hanoi");
        request.setShippingInstruction("Leave at the door");

        Set<ConstraintViolation<PlaceOrderRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
        assertEquals("phone", violations.iterator().next().getPropertyPath().toString());
    }
}

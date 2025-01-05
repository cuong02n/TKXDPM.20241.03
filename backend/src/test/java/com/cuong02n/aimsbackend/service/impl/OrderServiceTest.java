package com.cuong02n.aimsbackend.service.impl;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private List<OrderProduct> orderProducts;

    @BeforeEach
    void setUp() {
        order = new Order();
        orderProducts = new ArrayList<>();
        order.setOrderProducts(orderProducts);
    }

    // Helper method to access private methods using Reflection
    private <T> T invokePrivateMethod(String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = OrderService.class.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return (T) method.invoke(orderService, args);
        } catch (Exception e) {
            throw new GeneralException("Failed to invoke private method: " + methodName, e);
        }
    }

    // Helper method to set private fields using Reflection
    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new GeneralException("Failed to set private field: " + fieldName, e);
        }
    }

    @Test
    void testCalculateBaseShippingFee_UrbanArea_WeightBelowInitial() {
        int fee = invokePrivateMethod("calculateBaseShippingFee", new Class<?>[]{double.class, String.class}, 2.0, "Hà Nội");
        assertEquals(22000, fee); // INITIAL_FEE_URBAN
    }

    @Test
    void testCalculateBaseShippingFee_UrbanArea_WeightAboveInitial() {
        int fee = invokePrivateMethod("calculateBaseShippingFee", new Class<?>[]{double.class, String.class}, 4.0, "Hà Nội");
        assertEquals(22000 + (2500 * 2), fee); // INITIAL_FEE_URBAN + additionalFee
    }

    @Test
    void testCalculateBaseShippingFee_RuralArea_WeightBelowInitial() {
        int fee = invokePrivateMethod("calculateBaseShippingFee", new Class<?>[]{double.class, String.class}, 2.0, "Đà Nẵng");
        assertEquals(30000, fee); // INITIAL_FEE_RURAL
    }

    @Test
    void testCalculateBaseShippingFee_RuralArea_WeightAboveInitial() {
        int fee = invokePrivateMethod("calculateBaseShippingFee", new Class<?>[]{double.class, String.class}, 4.0, "Đà Nẵng");
        assertEquals(30000 + (2500 * 2), fee); // INITIAL_FEE_RURAL + additionalFee
    }

    @Test
    void testCalculateShippingFee_UrbanArea_NoRush() {
        order.setProvince("Hà Nội");

        // Create OrderProduct using Reflection to set private fields
        OrderProduct product = new OrderProduct();
        setPrivateField(product, "quantity", 1);
        setPrivateField(product, "product", createProduct(1L, 50000, 2.0));
        setPrivateField(product, "isRush", false);

        orderProducts.add(product);

        int fee = invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, order);
        assertEquals(22000, fee); // INITIAL_FEE_URBAN
    }

    @Test
    void testCalculateShippingFee_UrbanArea_WithRush() {
        order.setProvince("Hà Nội");

        // Create rush product using Reflection to set private fields
        OrderProduct rushProduct = new OrderProduct();
        setPrivateField(rushProduct, "quantity", 1);
        setPrivateField(rushProduct, "product", createProduct(1L, 50000, 2.0));
        setPrivateField(rushProduct, "isRush", true);

        // Create normal product using Reflection to set private fields
        OrderProduct normalProduct = new OrderProduct();
        setPrivateField(normalProduct, "quantity", 1);
        setPrivateField(normalProduct, "product", createProduct(2L, 30000, 1.0));
        setPrivateField(normalProduct, "isRush", false);

        orderProducts.add(rushProduct);
        orderProducts.add(normalProduct);

        int fee = invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, order);
        assertEquals(22000 + 10000, fee); // INITIAL_FEE_URBAN + RUSH_SHIPPING_FEE
    }

    @Test
    void testCalculateShippingFee_FreeShipping() {
        order.setProvince("Hà Nội");

        // Create product using Reflection to set private fields
        OrderProduct product = new OrderProduct();
        setPrivateField(product, "quantity", 1);
        setPrivateField(product, "product", createProduct(1L, 150000, 2.0));
        setPrivateField(product, "isRush", false);

        orderProducts.add(product);

        int fee = invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, order);
        assertEquals(0, fee); // Free shipping
    }

    @Test
    void testCalculateShippingFee_ThrowsException_WhenOrderIsNull() {
        assertThrows(GeneralException.class, () -> {
            invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, (Order) null);
        });
    }

    @Test
    void testCalculateShippingFee_ThrowsException_WhenOrderProductsIsEmpty() {
        order.setOrderProducts(new ArrayList<>());
        assertThrows(GeneralException.class, () -> {
            invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, order);
        });
    }

    @Test
    void testCalculateShippingFee_ThrowsException_WhenProductWeightIsInvalid() {
        order.setProvince("Hà Nội");

        // Create product with invalid weight using Reflection to set private fields
        OrderProduct product = new OrderProduct();
        setPrivateField(product, "quantity", 1);
        setPrivateField(product, "product", createProduct(1L, 50000, -1.0)); // Invalid weight
        setPrivateField(product, "isRush", false);

        orderProducts.add(product);

        assertThrows(GeneralException.class, () -> {
            invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, order);
        });
    }

    @Test
    void testCalculateShippingFee_ThrowsException_WhenProductQuantityIsInvalid() {
        order.setProvince("Hà Nội");

        // Create product with invalid quantity using Reflection to set private fields
        OrderProduct product = new OrderProduct();
        setPrivateField(product, "quantity", 0); // Invalid quantity
        setPrivateField(product, "product", createProduct(1L, 50000, 2.0));
        setPrivateField(product, "isRush", false);

        orderProducts.add(product);

        assertThrows(GeneralException.class, () -> {
            invokePrivateMethod("calculateShippingFee", new Class<?>[]{Order.class}, order);
        });
    }

    private Product createProduct(long id, int price, double weight) {
        Product product = new Product();
        product.setId(id);
        product.setPrice(price);
        product.setWeight(weight);
        return product;
    }
}
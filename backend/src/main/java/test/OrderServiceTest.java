package test;
import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.OrderProduct;
import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.service.impl.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Product product;
    private Method calculateShippingFeeMethod;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        order = new Order();
        product = new Product();
        product.setWeight(2.0);
        product.setPrice(50000);
        product.setId(1L);

        // Get access to the private method
        calculateShippingFeeMethod = OrderService.class.getDeclaredMethod("calculateShippingFee", Order.class);
        calculateShippingFeeMethod.setAccessible(true);
    }

    @Test
    void calculateShippingFee_urbanArea_basicWeight() throws Exception {
        // Arrange
        order.setProvince("Hà Nội");
        OrderProduct orderProduct = createOrderProduct(product, 1, false);
        order.setOrderProducts(List.of(orderProduct));

        // Act
        int shippingFee = (int) calculateShippingFeeMethod.invoke(orderService, order);

        // Assert
        assertEquals(22000, shippingFee); // INITIAL_FEE_URBAN for weight <= 3.0
    }

    @Test
    void calculateShippingFee_ruralArea_basicWeight() throws Exception {
        // Arrange
        order.setProvince("Đà Nẵng");
        OrderProduct orderProduct = createOrderProduct(product, 1, false);
        order.setOrderProducts(List.of(orderProduct));

        // Act
        int shippingFee = (int) calculateShippingFeeMethod.invoke(orderService, order);

        // Assert
        assertEquals(30000, shippingFee); // INITIAL_FEE_RURAL for weight <= 3.0
    }

    @Test
    void calculateShippingFee_urbanArea_extraWeight() throws Exception {
        // Arrange
        order.setProvince("Hồ Chí Minh");
        product.setWeight(4.0); // > INITIAL_WEIGHT_ROOT
        OrderProduct orderProduct = createOrderProduct(product, 1, false);
        order.setOrderProducts(List.of(orderProduct));

        // Act
        int shippingFee = (int) calculateShippingFeeMethod.invoke(orderService, order);

        // Assert
        assertEquals(27000, shippingFee); // INITIAL_FEE_URBAN + (2 * ADDITIONAL_FEE_PER_WEIGHT)
    }

    @Test
    void calculateShippingFee_withRushOrder() throws Exception {
        // Arrange
        order.setProvince("Hà Nội");
        OrderProduct normalProduct = createOrderProduct(product, 1, false);
        OrderProduct rushProduct = createOrderProduct(product, 1, true);
        order.setOrderProducts(List.of(normalProduct, rushProduct));

        // Act
        int shippingFee = (int) calculateShippingFeeMethod.invoke(orderService, order);

        // Assert
        assertEquals(32000, shippingFee); // INITIAL_FEE_URBAN + RUSH_SHIPPING_FEE
    }

    @Test
    void calculateShippingFee_freeShippingThreshold() throws Exception {
        // Arrange
        order.setProvince("Hà Nội");
        product.setPrice(150000); // Above FREE_SHIPPING_THRESHOLD
        OrderProduct orderProduct = createOrderProduct(product, 1, false);
        order.setOrderProducts(List.of(orderProduct));

        // Act
        int shippingFee = (int) calculateShippingFeeMethod.invoke(orderService, order);

        // Assert
        assertEquals(0, shippingFee); // Free shipping as base fee <= MAX_SHIPPING_DISCOUNT
    }

    @Test
    void calculateShippingFee_nullOrder() {
        // Act & Assert
        InvocationTargetException exception = assertThrows(InvocationTargetException.class,
                () -> calculateShippingFeeMethod.invoke(orderService, (Order)null));

        assertTrue(exception.getCause() instanceof GeneralException);
        assertEquals("Order cannot be null", exception.getCause().getMessage());
    }

    @Test
    void calculateShippingFee_emptyProducts() {
        // Arrange
        order.setProvince("Hà Nội");
        order.setOrderProducts(new ArrayList<>());

        // Act & Assert
        InvocationTargetException exception = assertThrows(InvocationTargetException.class,
                () -> calculateShippingFeeMethod.invoke(orderService, order));

        assertTrue(exception.getCause() instanceof GeneralException);
        assertEquals("Order products list cannot be empty for order ID: " + order.getOrderId(),
                exception.getCause().getMessage());
    }

    private OrderProduct createOrderProduct(Product product, int quantity, boolean isRush) {
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .order(order)
                .quantity(quantity)
                .isRush(isRush)
                .key(new OrderProduct.OrderProductKey(product.getId(), order.getOrderId()))
                .build();
        return orderProduct;
    }
}
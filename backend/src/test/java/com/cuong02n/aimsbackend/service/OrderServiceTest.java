package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.Invoice;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.InvoiceRepository;
import com.cuong02n.aimsbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder() {
        User user = new User();
        HashSet<Long> productIds = new HashSet<>();
        productIds.add(1L);
        String address = "123 Main St";
        String phone = "1234567890";
        String province = "Province";
        String shippingInstruction = "Handle with care";

        List<ProductCart> cart = new ArrayList<>();
        ProductCart productCart = mock(ProductCart.class);
        when(productCart.getKey().getProductId()).thenReturn(1L);
        cart.add(productCart);

        when(cartService.getUserCart(user)).thenReturn(cart);
        when(orderRepository.findByUser(user)).thenReturn(null);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(new Invoice());

        Invoice invoice = orderService.placeOrder(user, productIds, address, phone, province, shippingInstruction);

        assertNotNull(invoice);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testPlaceRushOrder() {
        User user = new User();
        HashSet<Long> productIds = new HashSet<>();
        productIds.add(1L);
        int minute = 30;
        String address = "123 Main St";
        String phone = "1234567890";
        String province = "Province";
        String shippingInstruction = "Handle with care";

        List<ProductCart> cart = new ArrayList<>();
        ProductCart productCart = mock(ProductCart.class);
        when(productCart.getKey().getProductId()).thenReturn(1L);
        cart.add(productCart);

        when(cartService.getUserCart(user)).thenReturn(cart);
        when(orderRepository.findByUser(user)).thenReturn(null);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(new Invoice());

        Invoice invoice = orderService.placeRushOrder(user, productIds, minute, address, phone, province, shippingInstruction);

        assertNotNull(invoice);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testGetOrder() {
        User user = new User();
        List<Order> orders = new ArrayList<>();

        when(orderRepository.findAllByUser(user)).thenReturn(orders);

        List<Order> result = orderService.getOrder(user);

        assertEquals(orders, result);
        verify(orderRepository, times(1)).findAllByUser(user);
    }

    @Test
    public void testCheckOrderNotPaidExist() {
        User user = new User();
        Order order = new Order();

        when(orderRepository.findByUser(user)).thenReturn(order);

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            orderService.placeOrder(user, new HashSet<>(), "", "", "", "");
        });

        assertTrue(exception.getMessage().contains("There is a order you have not paid yet: "));
    }

    @Test
    public void testCheckPlaceOrderRequestInCart() {
        User user = new User();
        HashSet<Long> productIds = new HashSet<>();
        productIds.add(1L);

        List<ProductCart> cart = new ArrayList<>();
        ProductCart productCart = mock(ProductCart.class);
        when(productCart.getKey().getProductId()).thenReturn(1L);
        cart.add(productCart);

        when(cartService.getUserCart(user)).thenReturn(cart);

        assertDoesNotThrow(() -> {
            orderService.placeOrder(user, productIds, "", "", "", "");
        });
    }
}
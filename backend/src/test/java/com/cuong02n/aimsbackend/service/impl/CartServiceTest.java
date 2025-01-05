package com.cuong02n.aimsbackend.service.impl;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.ProductCartRepository;
import com.cuong02n.aimsbackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private ProductCartRepository productCartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private ProductCart productCart;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");

        productCart = new ProductCart();
        productCart.setUser(user);

        product = new Product();
        product.setId(1L);
    }

    @Test
    void testGetUserCart() {
        when(productCartRepository.findAllByKey_UserEmail(user.getEmail())).thenReturn(Collections.singletonList(productCart));

        List<ProductCart> result = cartService.getUserCart(user);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getUser());

        verify(productCartRepository, times(1)).findAllByKey_UserEmail(user.getEmail());
    }

    @Test
    void testAddToCart() {
        long productId = 1L;
        int quantity = 2;

        // Mock the productRepository.findById() to return a valid product
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Mock the productCartRepository.existsByKey_UserEmailAndKey_ProductId() to return false
        when(productCartRepository.existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId)).thenReturn(false);

        cartService.addToCart(user, productId, quantity);

        verify(productCartRepository, times(1)).existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId);
        verify(productCartRepository, times(1)).save(any(ProductCart.class));
    }

    @Test
    void testAddToCart_ProductAlreadyInCart() {
        long productId = 1L;
        int quantity = 2;

        // Mock the productCartRepository.existsByKey_UserEmailAndKey_ProductId() to return true
        when(productCartRepository.existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId)).thenReturn(true);

        assertThrows(GeneralException.class, () -> cartService.addToCart(user, productId, quantity));

        verify(productCartRepository, times(1)).existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId);
        verify(productCartRepository, never()).save(any(ProductCart.class));
    }
}
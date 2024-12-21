package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.ProductCartRepository;
import com.cuong02n.aimsbackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private ProductCartRepository productCartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserCart() {
        User user = new User();
        user.setEmail("test@example.com");
        List<ProductCart> cart = List.of(new ProductCart());

        when(productCartRepository.findAllByKey_UserEmail(user.getEmail())).thenReturn(cart);

        List<ProductCart> result = cartService.getUserCart(user);

        assertEquals(cart, result);
        verify(productCartRepository, times(1)).findAllByKey_UserEmail(user.getEmail());
    }

    @Test
    public void testAddToCart() {
        User user = new User();
        user.setEmail("test@example.com");
        long productId = 1L;
        int quantity = 2;

        when(productCartRepository.existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId)).thenReturn(false);
        when(productRepository.findById(productId)).thenReturn(Optional.of(new ProductCart().getProduct()));

        cartService.addToCart(user, productId, quantity);

        verify(productCartRepository, times(1)).save(any(ProductCart.class));
    }

    @Test
    public void testAddToCartProductExists() {
        User user = new User();
        user.setEmail("test@example.com");
        long productId = 1L;

        when(productCartRepository.existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId)).thenReturn(true);

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            cartService.addToCart(user, productId, 1);
        });

        assertEquals("This product: %d is already in the cart".formatted(productId), exception.getMessage());
    }

    @Test
    public void testCheckProductExistInCart() {
        User user = new User();
        user.setEmail("test@example.com");
        long productId = 1L;

        when(productCartRepository.existsByKey_UserEmailAndKey_ProductId(user.getEmail(), productId)).thenReturn(true);

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            cartService.addToCart(user, productId, 1);
        });

        assertEquals("This product: %d is already in the cart".formatted(productId), exception.getMessage());
    }
}
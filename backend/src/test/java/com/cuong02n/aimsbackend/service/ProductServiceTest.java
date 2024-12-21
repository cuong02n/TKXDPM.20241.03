package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.*;
import com.cuong02n.aimsbackend.repository.ProductRepository;
import com.cuong02n.aimsbackend.repository.ReviewRepository;
import com.cuong02n.aimsbackend.repository.WishListRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MediaService mediaService;

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReview() {
        User user = new User();
        long productId = 1L;
        String content = "Great product!";
        int star = 5;
        List<MultipartFile> medias = List.of(mock(MultipartFile.class));

        when(httpServletRequest.getAttribute("user")).thenReturn(user);
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

        productService.review(medias, productId, content, star);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testGetProduct() {
        long productId = 1L;
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProduct(productId);

        assertEquals(product, result);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = List.of(new Product());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(products, result);
    }

    @Test
    public void testAddWishList() {
        User user = new User();
        user.setEmail("test@example.com");
        long productId = 1L;

        when(wishListRepository.existsByUserAndKey_ProductId(user, productId)).thenReturn(false);
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));

        productService.addWishList(user, productId);

        verify(wishListRepository, times(1)).save(any(FavoriteProductUser.class));
    }

    @Test
    public void testAddWishListProductExists() {
        User user = new User();
        user.setEmail("test@example.com");
        long productId = 1L;

        when(wishListRepository.existsByUserAndKey_ProductId(user, productId)).thenReturn(true);

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            productService.addWishList(user, productId);
        });

        assertEquals("Product already exists in your wish list: %s".formatted(productId), exception.getMessage());
    }

    @Test
    public void testGetWishList() {
        User user = new User();
        user.setEmail("test@example.com");
        List<FavoriteProductUser> wishList = List.of(new FavoriteProductUser());

        when(wishListRepository.findAllByKey_UserEmail(user.getEmail())).thenReturn(wishList);

        List<FavoriteProductUser> result = productService.getWishList(user);

        assertEquals(wishList, result);
    }
}
package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.dto.response.ProductDto;
import com.cuong02n.aimsbackend.model.entity.FavoriteProductUser;
import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.Review;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.ProductRepository;
import com.cuong02n.aimsbackend.repository.ReviewRepository;
import com.cuong02n.aimsbackend.repository.WishListRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ReviewRepository reviewRepository;
    private final HttpServletRequest httpServletRequest;
    private final ProductRepository productRepository;
    private final MediaService mediaService;
    private final WishListRepository wishListRepository;
    private final ModelMapper modelMapper;
    @Value("${aims.review.max-content-length}")
    private int maxContentReview;
    @Value("${aims.review.max-media-count}")
    private int maxMediaCountReview;
    @Value("${aims.review.supported-media}")
    private List<String> supportedMediaTypeReview;

    public void review(List<MultipartFile> medias, long productId, String content, Integer star) {

        checkContent(content);
        checkStar(star);
        checkMedia(medias);
        checkProduct(productId);

        Product product = productRepository.findById(productId).orElseThrow();
        User user = (User) httpServletRequest.getAttribute("user");

        checkReviewExisted(user, product);

        Review review =
                Review
                        .builder()
                        .user((User) httpServletRequest.getAttribute("user"))
                        .product(productRepository.findById(productId).orElseThrow())
                        .star(star)
                        .content(content)
                        .mediaUrls(
                                medias
                                        .stream()
                                        .map(mediaService::saveMedia)
                                        .toList()
                        )
                        .build();

        reviewRepository.save(review);
    }

    public Product getProduct(long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public List<ProductDto> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(p->modelMapper.map(p, ProductDto.class))
                .toList();
    }

    private void checkReviewExisted(User user, Product product) {
        if (reviewRepository.existsByUserAndProduct(user, product)) {
            throw new GeneralException("You reviewed this product before");
        }
    }

    private void checkMedia(List<MultipartFile> medias) {
        if (medias.size() > maxMediaCountReview) {
            throw new GeneralException("Media count exceeds max limit: %d > %d".formatted(medias.size(), maxMediaCountReview));
        }
        for (MultipartFile media : medias) {
            if (!supportedMediaTypeReview.contains(media.getContentType())) {
                throw new UnsupportedMediaTypeStatusException("Not supported media type: " + media.getContentType());
            }
        }
    }

    private void checkStar(int star) {
        int minStar = 1;
        int maxStar = 5;
        if (star > maxStar || star < minStar) {
            throw new GeneralException("Star must be between %d and %d".formatted(minStar, maxStar));
        }
    }

    private void checkContent(String content) {
        if (content.length() >= maxContentReview) {
            throw new GeneralException("Not support more than %d characters review".formatted(maxContentReview));
        }
    }

    private void checkProduct(long productId) {
        if (!productRepository.existsById(productId)) {
            throw new GeneralException("Product does not exist with id: %s".formatted(productId));
        }
    }

    public void addWishList(User user, long productId) {
        if (wishListRepository.existsByUserAndKey_ProductId(user, productId)) {
            throw new GeneralException("Product already exists in your wish list: %s".formatted(productId));
        }
        FavoriteProductUser favor = new FavoriteProductUser();
        favor.setKey(new FavoriteProductUser.WishListKey(user.getEmail(), productId));
        wishListRepository.save(favor);
    }

    public List<FavoriteProductUser> getWishList(User user) {
        return wishListRepository.findAllByKey_UserEmail(user.getEmail());
    }
}

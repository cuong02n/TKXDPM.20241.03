package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.entity.FavoriteProductUser;
import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    void review(List<MultipartFile> medias, long productId, String content, Integer star);

    Product getProduct(long productId);

    List<Product> getAllProducts();

    void addWishList(User user, long productId);

    List<FavoriteProductUser> getWishList(User user);

    List<Product> searchProducts(String query);
}

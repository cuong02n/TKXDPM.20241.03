package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.FavoriteProductUser;
import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<FavoriteProductUser, FavoriteProductUser.WishListKey> {
    boolean existsByUserAndKey_ProductId(User user, String productId);

    List<FavoriteProductUser> findAllByKey_UserEmail(String email);
}

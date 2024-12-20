package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCartRepository extends JpaRepository<ProductCart, ProductCart.ProductCartKey> {
    boolean existsByKey_UserEmailAndKey_ProductId(String userEmail, long productId);
    List<ProductCart> findAllByKey_UserEmail(String userEmail);
}

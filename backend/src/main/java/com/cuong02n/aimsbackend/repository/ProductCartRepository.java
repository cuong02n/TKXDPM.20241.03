package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCartRepository extends JpaRepository<ProductCart, ProductCart.ProductCartKey> {
    boolean existsByKey_UserEmailAndKey_ProductId(String userEmail, String productId);
}

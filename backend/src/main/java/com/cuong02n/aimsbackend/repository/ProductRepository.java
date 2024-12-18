package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsById(long productId);

    @NotNull
    Optional<Product> findById(long productId);
}

package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
<<<<<<< HEAD
    boolean existsById(long productId);

    @NotNull
    Optional<Product> findById(long productId);
=======
    boolean existsById(@NotNull String productId);

    @NotNull
    Optional<Product> findById(@NotNull String productId);
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
}

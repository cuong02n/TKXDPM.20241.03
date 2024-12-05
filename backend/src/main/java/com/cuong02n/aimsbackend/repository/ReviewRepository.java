package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.Review;
import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByUserAndProduct(User user, Product product);
}

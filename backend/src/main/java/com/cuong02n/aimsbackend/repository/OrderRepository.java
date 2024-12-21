package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUser(User user);
    List<Order> findAllByUser(User user);
}

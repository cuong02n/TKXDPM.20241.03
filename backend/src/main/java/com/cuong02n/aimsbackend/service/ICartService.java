package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICartService {
    List<ProductCart> getUserCart(User user);

    void addToCart(User user, long productId, int quantity);
}

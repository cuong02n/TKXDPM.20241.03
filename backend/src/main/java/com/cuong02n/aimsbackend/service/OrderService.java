package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    public Order placeOrder(User user, HashSet<String> productIds) {
        List<ProductCart> cart = user.getUserCart().getProductCarts();

        checkPlaceOrderRequestInCart(cart, productIds);

        return createNewOrder(cart, productIds);
    }

    private Order createNewOrder(List<ProductCart> productCarts, HashSet<String> productIds) {
        return null;
        // TODO
    }

    private void checkPlaceOrderRequestInCart(List<ProductCart> productCarts, HashSet<String> productIds) {
        List<String> productIdInCart = productCarts.stream().map(c -> c.getKey().getProductId()).toList();
        if (!new HashSet<>(productIdInCart).containsAll(productIds)) {
            throw new GeneralException("Place Order request must be exist in cart: [" + productIdInCart + "] not contains [" + productIds + "]");
        }
    }
}

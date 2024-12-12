package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.OrderProduct;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order placeOrder(User user, HashSet<String> productIds) {

        checkPlaceOrderRequestInCart(user.getUserCart().getProductCarts(), productIds);

        checkOrderNotPaidExist(user);

        return createNewOrder(user, productIds);
    }

    public Order placeRushOrder(User user, HashSet<String> productIds,int minute, String address) {
        // check the address

        return null;
    }

    private Order createNewOrder(User user, HashSet<String> productIds) {
        List<ProductCart> cart = user.getUserCart().getProductCarts();
        Order order = new Order();
        order.setUser(user);

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (ProductCart productCart : cart) {
            if (productIds.contains(productCart.getKey().getProductId())) {
                orderProducts.add(
                        OrderProduct
                                .builder()
                                .order(order)
                                .product(productCart.getProduct())
                                .quantity(productCart.getQuantity())
                                .build()
                );
            }
        }
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
        return order;
    }

    private void checkOrderNotPaidExist(User user) {
        Order notPaidOrder = orderRepository.findByUserAndIsPaidFalse(user);
        if (notPaidOrder != null) {
            throw new GeneralException("There is a order you have not paid yet: " + notPaidOrder.getOrderId());
        }
    }

    private void checkPlaceOrderRequestInCart(List<ProductCart> productCarts, HashSet<String> productIds) {
        List<String> productIdInCart = productCarts.stream().map(c -> c.getKey().getProductId()).toList();
        if (!new HashSet<>(productIdInCart).containsAll(productIds)) {
            throw new GeneralException("Place Order request must be exist in cart: [" + productIdInCart + "] not contains [" + productIds + "]");
        }
    }
}

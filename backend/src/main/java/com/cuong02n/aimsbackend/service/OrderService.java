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

    public Order placeOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction) {

        checkPlaceOrderRequestInCart(user.getUserCart().getProductCarts(), productIds);

        checkOrderNotPaidExist(user);

        return createNewOrder(user, productIds, address, phone, province, shippingInstruction);
    }

    public Order placeRushOrder(User user, HashSet<Long> productIds, int minute, String address, String phone, String province, String shippingInstruction) {
        checkPlaceOrderRequestInCart(user.getUserCart().getProductCarts(), productIds);
        checkOrderNotPaidExist(user);
        return createNewOrder(user, productIds, address, phone, province, shippingInstruction, minute);
    }

    private Order createNewOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction) {
        return createNewOrder(user, productIds, address, phone, province, shippingInstruction, 0);
    }

    private Order createNewOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction, int timeInMinute) {
        List<ProductCart> cart = user.getUserCart().getProductCarts();
        Order order = new Order();

        order.setUser(user);
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (ProductCart productCart : cart) {
            if (productIds.contains(productCart.getKey().getProductId())) {
                orderProducts.add(
                        OrderProduct
                                .builder()
                                .product(productCart.getProduct())
                                .order(order)
                                .quantity(productCart.getQuantity())
                                .key(new OrderProduct.OrderProductKey(
                                        productCart.getKey().getProductId(),
                                        order.getOrderId())
                                )
                                .build()
                );
            }
        }
        order.setOrderProducts(orderProducts);
        order.setPaid(false);
        order.setAddress(address);
        order.setPhone(phone);
        order.setProvince(province);
        order.setShippingInstruction(shippingInstruction);
        order.setRush(timeInMinute != 0);
        order.setTimeInMinute(timeInMinute);

        orderRepository.save(order);
        return order;
    }

    public List<Order> getOrder(User user) {
        return orderRepository.findAllByUser(user);
    }

    private void checkOrderNotPaidExist(User user) {
        Order notPaidOrder = orderRepository.findByUserAndIsPaidFalse(user);
        if (notPaidOrder != null) {
            throw new GeneralException("There is a order you have not paid yet: " + notPaidOrder.getOrderId());
        }
    }

    private void checkPlaceOrderRequestInCart(List<ProductCart> productCarts, HashSet<Long> productIds) {
        List<Long> productIdInCart = productCarts.stream().map(c -> c.getKey().getProductId()).toList();
        if (!new HashSet<>(productIdInCart).containsAll(productIds)) {
            throw new GeneralException("Place Order request must be exist in cart: [" + productIdInCart + "] not contains [" + productIds + "]");
        }
    }
}

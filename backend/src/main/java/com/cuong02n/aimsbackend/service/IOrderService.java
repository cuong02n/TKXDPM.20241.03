package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.dto.request.PlaceOrderV2Request;
import com.cuong02n.aimsbackend.model.entity.Invoice;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

public interface IOrderService {
    Collection<Order> getOrder(User user);

    Invoice placeOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction);

    Invoice placeOrderV2(User user, PlaceOrderV2Request request);

    Invoice placeRushOrder(User user, HashSet<Long> productIds, int timeInMinute, String address, String phone, String province, String shippingInstruction);

    void deleteOrder(long orderId);
}

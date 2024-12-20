package com.cuong02n.aimsbackend.model.dto.request;

import lombok.Data;

import java.util.HashSet;

@Data
public class PlaceOrderRequest {
    HashSet<Long> productIds;
    String address;
    String phone;
    String province;
    String shippingInstruction;
}



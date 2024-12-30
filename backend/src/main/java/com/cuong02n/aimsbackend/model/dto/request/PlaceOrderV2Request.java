package com.cuong02n.aimsbackend.model.dto.request;

import lombok.Data;

import java.util.HashSet;

@Data
public class PlaceOrderV2Request {
    HashSet<Long> listProductRush;
    HashSet<Long> listProductNoRush;
    String address;
    String phone;
    String province;
    String shippingInstruction;
    int timeInMinute;
}

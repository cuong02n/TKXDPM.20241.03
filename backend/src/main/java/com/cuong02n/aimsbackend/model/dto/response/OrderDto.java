package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.OrderProduct;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    Long orderId;
    List<OrderProduct> orderProductDtos;
    boolean isPaid = false;

    private String address;

    private String phone;

    private String province;
    private String shippingInstruction;

    boolean isRush = false;
    int timeInMinute = 120;
}

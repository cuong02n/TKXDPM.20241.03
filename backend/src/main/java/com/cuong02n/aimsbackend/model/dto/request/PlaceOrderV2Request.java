package com.cuong02n.aimsbackend.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;

@Data
public class PlaceOrderV2Request {
    HashSet<Long> listProductRush;
    HashSet<Long> listProductNoRush;
    @NotBlank(message = "Address cannot be null")
    @Size(max = 100, message = "Address must be under 101 characters")
    String address;
    String phone;
    String province;
    String shippingInstruction;
    @Positive(message = "Time in minute must be positive")
    int timeInMinute;
}

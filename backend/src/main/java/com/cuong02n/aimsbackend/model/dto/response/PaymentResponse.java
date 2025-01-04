package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.constant.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private String orderId;
    private String totalPrice;
    private LocalDateTime paymentTime;
    private String transactionId;
    private PaymentStatus status;
}

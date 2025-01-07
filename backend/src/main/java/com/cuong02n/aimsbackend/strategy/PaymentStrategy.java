package com.cuong02n.aimsbackend.strategy;

import com.cuong02n.aimsbackend.model.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentStrategy {
    String createPaymentUrl(Long total, String orderInfo, String urlReturn);
    PaymentResponse processPaymentReturn(HttpServletRequest request);
}

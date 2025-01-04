package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    String createPayment(String provider, Long total, String orderInfo, String urlReturn);
    PaymentResponse processPaymentReturn(String provider, HttpServletRequest request);
}

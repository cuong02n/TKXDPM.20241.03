package com.cuong02n.aimsbackend.service.impl;

import com.cuong02n.aimsbackend.model.dto.response.PaymentResponse;
import com.cuong02n.aimsbackend.service.IPaymentService;
import com.cuong02n.aimsbackend.strategy.PaymentStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService implements IPaymentService {
    private final Map<String, PaymentStrategy> paymentStrategies;

    public PaymentService(Map<String, PaymentStrategy> strategies) {
        this.paymentStrategies = strategies;
    }
    @Override
    public String createPayment(String provider, Long total, String orderInfo, String urlReturn) {
        PaymentStrategy strategy = paymentStrategies.get(provider);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment provider: " + provider);
        }
        return strategy.createPaymentUrl(total, orderInfo, urlReturn);
    }
    @Override
    public PaymentResponse processPaymentReturn(String provider, HttpServletRequest request) {
        PaymentStrategy strategy = paymentStrategies.get(provider);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment provider: " + provider);
        }
        return strategy.processPaymentReturn(request);
    }
}

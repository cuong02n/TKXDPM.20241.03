package com.cuong02n.aimsbackend.strategy.impl;

import com.cuong02n.aimsbackend.constant.PaymentStatus;
import com.cuong02n.aimsbackend.model.dto.response.PaymentResponse;
import com.cuong02n.aimsbackend.strategy.PaymentStrategy;
import com.cuong02n.aimsbackend.subsystem.vnpay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component("vnpay")
public class VNPayStrategy implements PaymentStrategy {
    private final VNPayService vnPayService;

    public VNPayStrategy(VNPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

    @Override
    public String createPaymentUrl(Long total, String orderInfo, String urlReturn) {
        return vnPayService.createOrder(total, orderInfo, urlReturn);
    }

    @Override
    public PaymentResponse processPaymentReturn(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount")
                .substring(0, request.getParameter("vnp_Amount").length() - 2);

        LocalDateTime paymentDateTime = LocalDateTime.parse(
                paymentTime,
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        );

        return PaymentResponse.builder()
                .orderId(orderInfo)
                .totalPrice(totalPrice)
                .paymentTime(paymentDateTime)
                .transactionId(transactionId)
                .status(PaymentStatus.fromValue(paymentStatus))
                .build();
    }
}

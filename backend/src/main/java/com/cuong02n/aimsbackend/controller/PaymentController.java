package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.constant.PaymentStatus;
import com.cuong02n.aimsbackend.model.dto.response.PaymentResponse;
import com.cuong02n.aimsbackend.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Value("${aims.frontend.base-url}")
    private String frontEndBaseUrl;

    @PostMapping("/{provider}/create")
    public ResponseEntity<String> createPayment(
            @PathVariable String provider,
            @RequestParam("amount") Long orderTotal,
            @RequestParam("orderInfo") String orderInfo,
            HttpServletRequest request
    ) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String paymentUrl = paymentService.createPayment(provider, orderTotal, orderInfo, baseUrl);
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/{provider}/status")
    public ResponseEntity<?> paymentStatus(
            @PathVariable String provider,
            HttpServletRequest request
    ) {
        PaymentResponse response = paymentService.processPaymentReturn(provider, request);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(frontEndBaseUrl)
                .path(response.getStatus() == PaymentStatus.SUCCESS ?
                        "/payment/success" : "/payment/failure")
                .queryParam("orderId", response.getOrderId())
                .queryParam("totalPrice", response.getTotalPrice())
                .queryParam("paymentTime", response.getPaymentTime().toString())
                .queryParam("transactionId", response.getTransactionId())
                .queryParam("status", response.getStatus().getValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(builder.toUriString()));


        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }
}

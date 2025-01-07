package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.constant.PaymentStatus;
import com.cuong02n.aimsbackend.model.dto.response.PaymentResponse;
import com.cuong02n.aimsbackend.model.entity.Order;
import com.cuong02n.aimsbackend.repository.OrderRepository;
import com.cuong02n.aimsbackend.service.IEmailService;
import com.cuong02n.aimsbackend.service.IPaymentService;
import com.cuong02n.aimsbackend.service.impl.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final IPaymentService paymentService;
    private final OrderRepository orderRepository;
    private final IEmailService emailService;
    private String generatePaymentEmailContent(String customerName, String paymentResultUrl) {
        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Payment Notification</title>
        </head>
        <body style="margin: 0; padding: 0; background-color: #f4f4f4; font-family: Arial, sans-serif;">
            <table role="presentation" width="100%%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="padding: 20px 0;">
                        <table role="presentation" align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                            <tr>
                                <td style="padding: 40px 30px;">
                                    <h1 style="color: #333333; font-size: 24px; margin: 0 0 20px 0;">Payment Notification</h1>
                                    <p style="color: #666666; font-size: 16px; line-height: 1.5; margin: 0 0 20px 0;">
                                        Dear %s,
                                    </p>
                                    <p style="color: #666666; font-size: 16px; line-height: 1.5; margin: 0 0 20px 0;">
                                        Thank you for your payment. To view your payment result, please click the button below.
                                    </p>
                                    <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%%">
                                        <tr>
                                            <td align="center" style="padding: 20px 0;">
                                                <table role="presentation" border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td align="center" bgcolor="#007bff" style="border-radius: 4px;">
                                                            <a href="%s" target="_blank" style="display: inline-block; padding: 16px 36px; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 4px; background-color: #007bff;">View Payment Result</a>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                    <p style="color: #666666; font-size: 16px; line-height: 1.5; margin: 20px 0 0 0;">
                                        If you did not make this payment or have any questions, please contact our support team.
                                    </p>
                                    <p style="color: #666666; font-size: 16px; line-height: 1.5; margin: 20px 0 0 0;">
                                        Best regards,<br>
                                        Your Company Name
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """.formatted(customerName, paymentResultUrl);
    }

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


        String resultPath = response.getStatus() == PaymentStatus.SUCCESS ?
                "/payment/success" : "/payment/failure";

        String paymentResultUrl = UriComponentsBuilder.fromHttpUrl(frontEndBaseUrl)
                .path(resultPath)
                .queryParam("orderId", response.getOrderId())
                .queryParam("totalPrice", response.getTotalPrice())
                .queryParam("paymentTime", response.getPaymentTime().toString())
                .queryParam("transactionId", response.getTransactionId())
                .queryParam("status", response.getStatus().getValue())
                .build()
                .toUriString();

        if (response.getStatus() == PaymentStatus.SUCCESS) {

            Order order = orderRepository.findById(Long.valueOf(response.getOrderId()))
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            String userEmail = orderRepository.findUserEmailByOrderId(Long.parseLong(response.getOrderId()));

            String customerName = "Customer";
            String customerEmail = order.getUser().getEmail();


            String emailContent = generatePaymentEmailContent(customerName, paymentResultUrl);
            emailService.sendMail(
                    customerEmail,
                    "Payment Notification",
                    emailContent,
                    true // isHtml = true
            );
        }



        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(paymentResultUrl));


        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }


}

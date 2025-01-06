package com.cuong02n.aimsbackend.subsystem.vnpay;

import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.IEmailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RequestMapping("/api/vnpay")
@Controller
@RequiredArgsConstructor
public class VNPayController {
    private final VNPayService vnPayService;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final IEmailService emailService;
    @Value("${aims.frontend.base-url}")
    private String frontEndBaseUrl;

    @GetMapping("")
    public String home() {
        return "index";
    }


    @PostMapping("/submitOrder")
    public ResponseEntity<String> submitOrder(@RequestParam("amount") Long orderTotal,
                                              @RequestParam("orderInfo") String orderInfo) {
        User user = (User) httpServletRequest.getAttribute("user");

        String baseUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
        //String vnpayUrl = vnPayService.createOrder(orderTotal, user.getId()+"_"+Integer.parseInt(orderInfo), baseUrl);
        String vnpayUrl = vnPayService.createOrder(user, orderTotal, orderInfo, baseUrl);
        Cookie nameC = new Cookie("aims-user-name", user.getName());
        nameC.setHttpOnly(true);
        nameC.setSecure(false);
        nameC.setPath("/");
        nameC.setMaxAge(3600);
        httpServletResponse.addCookie(nameC);
        Cookie emailC = new Cookie("aims-user-email", user.getEmail());
        emailC.setHttpOnly(true);
        emailC.setSecure(false);
        emailC.setPath("/");
        emailC.setMaxAge(3600);
        httpServletResponse.addCookie(emailC);
        httpServletResponse.addHeader("Access-Control-Allow-Credentials","true");
        return ResponseEntity.ok(vnpayUrl);
    }

    @GetMapping("/vnpay-status")// ?asdhasjd=&hajsdfhadf=d&email=achjshd&name=
    public ResponseEntity<?> payStatus(
            HttpServletRequest httpServletRequest,
            @CookieValue("aims-user-name") String name,
            @CookieValue("aims-user-email") String email
    ) {
        int paymentStatus = vnPayService.orderReturn(httpServletRequest);

        String orderInfo = httpServletRequest.getParameter("vnp_OrderInfo");
        String paymentTime = httpServletRequest.getParameter("vnp_PayDate");
        String transactionId = httpServletRequest.getParameter("vnp_TransactionNo");
        String totalPrice = httpServletRequest.getParameter("vnp_Amount").substring(0, httpServletRequest.getParameter("vnp_Amount").length() - 2);
        // Định dạng lại thời gian thanh toán
        LocalDateTime paymentDateTime = LocalDateTime.parse(paymentTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String formattedPaymentTime = paymentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(frontEndBaseUrl)
                .path(paymentStatus == 1 ? "/payment/success" : "/payment/failure")
                .queryParam("orderId", orderInfo)
                .queryParam("totalPrice", totalPrice)
                .queryParam("paymentTime", formattedPaymentTime)
                .queryParam("transactionId", transactionId)
                .queryParam("status", paymentStatus);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(builder.toUriString()));

        if (paymentStatus == 1)
            emailService.sendPaymentSuccessMail(email,
                    name,
                    builder.toUriString());


        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }
}

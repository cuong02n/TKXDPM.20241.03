package com.cuong02n.aimsbackend.subsystem.vnpay;

import com.cuong02n.aimsbackend.service.IEmailService;
import com.cuong02n.aimsbackend.service.impl.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    private final HttpServletRequest request;
    @Value("${aims.frontend.base-url}")
    private String frontEndBaseUrl;
    @GetMapping("")
    public String home(){
        return "index";
    }

    @PostMapping("/submitOrder")
    public ResponseEntity<String> submitOrder(@RequestParam("amount") Long orderTotal,
                                              @RequestParam("orderInfo") String orderInfo) {
        //  UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        //   .getPrincipal();
        //    User user= userService.getById(getUserId(userDetails))
        //       .orElseThrow(()->new RuntimeException("user not found"));
        //userService.createTransaction(user,orderTotal,Integer.parseInt(orderInfo));
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        //String vnpayUrl = vnPayService.createOrder(orderTotal, user.getId()+"_"+Integer.parseInt(orderInfo), baseUrl);
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return ResponseEntity.ok(vnpayUrl);
    }

    @GetMapping("/vnpay-status")
    public ResponseEntity<?>  payStatus(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount").substring(0,request.getParameter("vnp_Amount").length()-2);
        // Định dạng lại thời gian thanh toán
        LocalDateTime paymentDateTime = LocalDateTime.parse(paymentTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String formattedPaymentTime = paymentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

//        // Tạo đối tượng ModelAndView và chọn view để render
//        ModelAndView modelAndView = new ModelAndView(paymentStatus == 1 ? "ordersuccess" : "orderfail");
//
//        // Thêm dữ liệu vào ModelAndView
//        modelAndView.addObject("orderId", orderInfo);
//        modelAndView.addObject("totalPrice", totalPrice);
//        modelAndView.addObject("paymentTime", formattedPaymentTime);
//        modelAndView.addObject("transactionId", transactionId);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(frontEndBaseUrl)
                .path(paymentStatus == 1 ? "/payment/success" : "/payment/failure")
                .queryParam("orderId", orderInfo)
                .queryParam("totalPrice", totalPrice)
                .queryParam("paymentTime", formattedPaymentTime)
                .queryParam("transactionId", transactionId)
                .queryParam("status", paymentStatus);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(builder.toUriString()));

//        emailService.sendMail(null,null,null,true);

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
//
//        return modelAndView;
    }
}

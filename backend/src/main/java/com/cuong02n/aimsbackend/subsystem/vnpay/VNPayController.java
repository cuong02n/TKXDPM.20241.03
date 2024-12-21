package com.cuong02n.aimsbackend.subsystem.vnpay;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@CrossOrigin
@RequestMapping("/api/vnpay")
@Controller
@RequiredArgsConstructor
public class VNPayController {
    private final VNPayService vnPayService;
    @GetMapping("")
    public String home(){
        return "index";
    }

    @PostMapping("/submitOrder")
    public ResponseEntity<String> submitOrder(@RequestParam("amount") Long orderTotal,
                                              @RequestParam("orderInfo") String orderInfo,
                                              HttpServletRequest request) {
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
    public String payStatus(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount").substring(0,request.getParameter("vnp_Amount").length()-2);
        // Định dạng lại thời gian thanh toán
        LocalDateTime paymentDateTime = LocalDateTime.parse(paymentTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String formattedPaymentTime = paymentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", formattedPaymentTime);
        model.addAttribute("transactionId", transactionId);
        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
   /* public String getUserId(UserDetails userDetails){

        String userName = userDetails.getUsername();
        Optional<User> user= userService.getByAccount(userName);
        if (!user.isPresent()) {
            return null;
        }

        return user.get().getId() ; */

}

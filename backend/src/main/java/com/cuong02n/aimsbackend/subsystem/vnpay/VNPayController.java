package com.cuong02n.aimsbackend.subsystem.vnpay;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestMapping("/api/vnpay")
@RestController
@RequiredArgsConstructor
public class VNPayController {
    private final VNPayService vnPayService;
    private final HttpServletRequest request;

    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("index");
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
    public ModelAndView payStatus() {
        int paymentStatus = vnPayService.orderReturn(request);

        // Lấy các tham số từ request
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // Định dạng lại thời gian thanh toán
        LocalDateTime paymentDateTime = LocalDateTime.parse(paymentTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String formattedPaymentTime = paymentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Tạo đối tượng ModelAndView và chọn view để render
        ModelAndView modelAndView = new ModelAndView(paymentStatus == 1 ? "ordersuccess" : "orderfail");

        // Thêm dữ liệu vào ModelAndView
        modelAndView.addObject("orderId", orderInfo);
        modelAndView.addObject("totalPrice", totalPrice);
        modelAndView.addObject("paymentTime", formattedPaymentTime);
        modelAndView.addObject("transactionId", transactionId);

        return modelAndView;
    }
   /* public String getUserId(UserDetails userDetails){

        String userName = userDetails.getUsername();
        Optional<User> user= userService.getByAccount(userName);
        if (!user.isPresent()) {
            return null;
        }

        return user.get().getId() ; */

}

package com.cuong02n.aimsbackend.subsystem.VNPay;

import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/api/vnpay")
@RestController
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;
    private final UserService userService;
    public VNPayController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @PostMapping("/submitOrder")
    public ResponseEntity<String> submitOrder(@RequestParam("amount") Long orderTotal,
                                              @RequestParam("orderInfo") String orderInfo,
                                              HttpServletRequest request){
        // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        // .getPrincipal();
        //  User user= userService.getById(getUserId(userDetails))
        //     .orElseThrow(()->new RuntimeException("user not found"));
        //userService.createTransaction(user,orderTotal,Integer.parseInt(orderInfo));
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        //String vnpayUrl = vnPayService.createOrder(orderTotal, user.getId()+"_"+Integer.parseInt(orderInfo), baseUrl);
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return ResponseEntity.ok(vnpayUrl);
    }

    @GetMapping("/vnpay-status")
    public ModelAndView payStatus(HttpServletRequest request) {
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

        return user.get().getId() ;
    } */
}

package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final HttpServletRequest httpServletRequest;


    @GetMapping("/my-orders")
    public ResponseEntity<?> getOrders() {
        return BaseResponse.ok(orderService.getOrder((User) httpServletRequest.getAttribute("user")));
    }

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(
            @RequestBody HashSet<String> productIds,
            @RequestBody String address,
            @RequestBody String phone,
            @RequestBody String province,
            @RequestBody String shippingInstruction
    ) {
        return BaseResponse.ok(
                orderService.placeOrder((User) httpServletRequest.getAttribute("user"), productIds, address, phone, province, shippingInstruction)
        );
    }

    @PostMapping("/place-rush-order")
    public ResponseEntity<?> placeRushOrder(
            @RequestBody HashSet<String> productIds,
            @RequestBody String address,
            @RequestBody String phone,
            @RequestBody String province,
            @RequestBody String shippingInstruction,
            @RequestBody int timeInMinute // Rush order
    ) {
        return BaseResponse.ok(orderService.placeRushOrder((User) httpServletRequest.getAttribute("user"), productIds, timeInMinute, address, phone, province, shippingInstruction));
    }

    @PostMapping("/pay-order")
    public ResponseEntity<?> payOrder() {
        return null;
    }

}

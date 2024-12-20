package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.request.PlaceOrderRequest;
import com.cuong02n.aimsbackend.model.dto.request.PlaceRushOrderRequest;
import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.dto.response.OrderDto;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final HttpServletRequest httpServletRequest;
    private final ModelMapper modelMapper;

    @GetMapping("/my-orders")
    public ResponseEntity<?> getOrders() {
        return BaseResponse.ok(
                orderService.getOrder((User) httpServletRequest.getAttribute("user"))
                        .stream()
                        .map(o -> modelMapper.map(o, OrderDto.class))
                        .toList()
        );
    }

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(
            @RequestBody PlaceOrderRequest request
    ) {
        orderService.placeOrder(
                (User) httpServletRequest.getAttribute("user"),
                request.getProductIds(),
                request.getAddress(),
                request.getPhone(),
                request.getProvince(),
                request.getShippingInstruction()
        );

        return BaseResponse.okMessage("Place Order successfully");
    }

    @PostMapping("/place-rush-order")
    public ResponseEntity<?> placeRushOrder(
            @RequestBody PlaceRushOrderRequest request
    ) {

        orderService.placeRushOrder(
                (User) httpServletRequest.getAttribute("user"),
                request.getProductIds(),
                request.getTimeInMinute(),
                request.getAddress(),
                request.getPhone(),
                request.getProvince(),
                request.getShippingInstruction()
        );
        return BaseResponse.okMessage("Place Rush Order successfully");
    }

    @PostMapping("/pay-order")
    public ResponseEntity<?> payOrder() {
        return null;
    }

}

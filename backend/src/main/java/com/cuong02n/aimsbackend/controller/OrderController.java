package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder() {
        return BaseResponse.ok(orderService.placeOrder((User) httpServletRequest.getAttribute("user")));
    }

}

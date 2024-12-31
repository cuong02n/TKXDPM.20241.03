package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.request.PlaceOrderRequest;
import com.cuong02n.aimsbackend.model.dto.request.PlaceOrderV2Request;
import com.cuong02n.aimsbackend.model.dto.request.PlaceRushOrderRequest;
import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.dto.response.InvoiceDto;
import com.cuong02n.aimsbackend.model.dto.response.OrderDto;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.IOrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final IOrderService orderService;
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
        var invoice = orderService.placeOrder(
                (User) httpServletRequest.getAttribute("user"),
                request.getProductIds(),
                request.getAddress(),
                request.getPhone(),
                request.getProvince(),
                request.getShippingInstruction()
        );

        return BaseResponse.ok(modelMapper.map(invoice, InvoiceDto.class));
    }

    @PostMapping("/place-order-v2")
    public ResponseEntity<?> placeOrderV2(
            @RequestBody PlaceOrderV2Request request
    ) {
        var invoice = orderService.placeOrderV2((User) httpServletRequest.getAttribute("user"), request);
        return BaseResponse.ok(modelMapper.map(invoice, InvoiceDto.class));
    }


    @PostMapping("/place-rush-order")
    public ResponseEntity<?> placeRushOrder(
            @RequestBody PlaceRushOrderRequest request
    ) {

        var invoice = orderService.placeRushOrder(
                (User) httpServletRequest.getAttribute("user"),
                request.getProductIds(),
                request.getTimeInMinute(),
                request.getAddress(),
                request.getPhone(),
                request.getProvince(),
                request.getShippingInstruction()
        );
        return BaseResponse.ok(modelMapper.map(invoice, InvoiceDto.class));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam long orderId) {
        orderService.deleteOrder(orderId);
        return BaseResponse.okMessage("Successfully delete order: " + orderId);
    }

    @PostMapping("/pay-order")
    public ResponseEntity<?> payOrder() {
        return null;
    }

}

package com.cuong02n.aimsbackend.controller;


import com.cuong02n.aimsbackend.model.dto.response.ProductCartDto;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final HttpServletRequest request;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<?> getUserCart() {
        return ResponseEntity.ok(
                cartService.getUserCart((User) request.getAttribute("user"))
                        .stream()
                        .map(p -> modelMapper.map(p, ProductCartDto.class))
                        .toList()
        );
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.addToCart((User) request.getAttribute("user"), productId, quantity);
        return ResponseEntity.ok().build();
    }
}

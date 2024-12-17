package com.cuong02n.aimsbackend.controller;


import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.CartService;
import com.cuong02n.aimsbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    final UserService userService;
    final HttpServletRequest request;
    final CartService cartService;

    @GetMapping()
    public ResponseEntity<?> getUserCart() {
        return ResponseEntity.ok(userService.getUserCart((User) request.getAttribute("user")));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody Long productId, @RequestBody int quantity) {
        cartService.addToCart((User) request.getAttribute("user"), productId, quantity);
        return ResponseEntity.ok().build();
    }
}

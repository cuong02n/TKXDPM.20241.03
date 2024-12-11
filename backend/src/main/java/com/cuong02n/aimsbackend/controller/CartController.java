package com.cuong02n.aimsbackend.controller;


import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    final UserService userService;
    final HttpServletRequest request;
    @GetMapping()
    public ResponseEntity<?> getUserCart(){
        return ResponseEntity.ok(userService.getUserCart((User)request.getAttribute("user")));
    }
}

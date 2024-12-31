package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.request.LoginRequest;
import com.cuong02n.aimsbackend.model.dto.request.RegisterRequest;
import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest register) {
        userService.register(register);
        return BaseResponse.ok("OTP will expired after 2 minutes");
    }

    @PostMapping("/verify-register")
    public ResponseEntity<?> verifyRegister(
            @RequestParam String email,
            @RequestParam String otp
    ) {
        userService.verifyRegister(email, otp);
        return BaseResponse.ok("Register successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return BaseResponse.ok(userService.login(request));
    }
}

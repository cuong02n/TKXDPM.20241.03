package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.dto.request.LoginRequest;
import com.cuong02n.aimsbackend.model.dto.request.RegisterRequest;
import com.cuong02n.aimsbackend.model.dto.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    boolean userExist(String email);

    boolean userActivated(String email);

    void register(RegisterRequest request);

    void verifyRegister(String email, String otp);

    LoginResponse login(LoginRequest request);
}

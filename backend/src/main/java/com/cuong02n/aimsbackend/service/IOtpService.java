package com.cuong02n.aimsbackend.service;

import org.springframework.stereotype.Service;

public interface IOtpService {
    String generateRegisterOtp(String email);

    void checkRegisterOtp(String email, String otp);
}

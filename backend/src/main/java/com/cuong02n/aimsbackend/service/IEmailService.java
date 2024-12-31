package com.cuong02n.aimsbackend.service;

import org.springframework.stereotype.Service;

public interface IEmailService {
    void sendRegisterEmail(String email, String name, String otp);

    void sendOrderEmail(String email, String orderId);

    void sendMail(String email, String subject, String content, boolean isHtml);
}

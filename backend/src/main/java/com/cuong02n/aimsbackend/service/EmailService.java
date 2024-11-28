package com.cuong02n.aimsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    final JavaMailSender mailSender;
    public void sendRegisterEmail(String email, String name, String otp) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("OTP đăng kí tài khoản AIMS");
        simpleMailMessage.setText("Xin chào %s, %s là mã OTP của bạn, nó sẽ hết hạn sau 2 phút nữa".formatted(name, otp));
        mailSender.send(simpleMailMessage);
    }
}

package com.cuong02n.aimsbackend.service.impl;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailService implements IEmailService {
    final JavaMailSender mailSender;

    public void sendRegisterEmail(String email, String name, String otp) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("OTP đăng kí tài khoản AIMS");
        simpleMailMessage.setText("Xin chào %s, %s là mã OTP của bạn, nó sẽ hết hạn sau 2 phút nữa".formatted(name, otp));
        mailSender.send(simpleMailMessage);
    }

    public void sendOrderEmail(String email, String orderId) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Bạn đã tạo thành công đơn hàng");
        simpleMailMessage.setText("Xin chào, bạn đã tạo thành công đơn hàng: mã đơn hàng: %s, hãy nhanh chóng thanh toán để đảm bảo còn đủ số lượng.".formatted(orderId));
        mailSender.send(simpleMailMessage);
    }

    public void sendMail(String email, String subject, String content, boolean isHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, isHtml);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new GeneralException("Cannot send email ",e);
        }
    }
}

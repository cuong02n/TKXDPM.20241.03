package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.service.impl.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendRegisterEmail() {
        String email = "test@example.com";
        String name = "Test";
        String otp = "123456";

        emailService.sendRegisterEmail(email, name, otp);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendOrderEmail() {
        String email = "test@example.com";
        String orderId = "ORD123456";

        emailService.sendOrderEmail(email, orderId);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
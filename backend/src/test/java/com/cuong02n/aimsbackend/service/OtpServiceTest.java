package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.AimsOtpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.jupiter.api.Assertions.*;

public class OtpServiceTest {

    @InjectMocks
    private OtpService otpService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateRegisterOtp() {
        String email = "test@example.com";
        String otp = otpService.generateRegisterOtp(email);

        assertNotNull(otp);
        assertEquals(OtpService.OTP_REGISTER_LENGTH, otp.length());
    }

    @Test
    public void testCheckRegisterOtp() {
        String email = "test@example.com";
        String otp = otpService.generateRegisterOtp(email);

        assertDoesNotThrow(() -> otpService.checkRegisterOtp(email, otp));
    }

    @Test
    public void testCheckRegisterOtpNotFound() {
        AimsOtpException exception = assertThrows(AimsOtpException.class, () -> {
            otpService.checkRegisterOtp("nonexistent@example.com", "123456");
        });

        assertEquals("Not found otp", exception.getMessage());
    }

    @Test
    public void testCheckRegisterOtpExpired() {
        String email = "test@example.com";
        String otp = otpService.generateRegisterOtp(email);

        // Simulate expired OTP
        otpService.registerOtpAndCreateTime.put(email, otp + "-" + (System.currentTimeMillis() - otpService.REGISTER_OTP_EXP - 1));

        AimsOtpException exception = assertThrows(AimsOtpException.class, () -> {
            otpService.checkRegisterOtp(email, otp);
        });

        assertEquals("EXPIRED", exception.getMessage());
    }
}
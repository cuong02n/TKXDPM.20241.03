package com.cuong02n.aimsbackend.service.impl;

import com.cuong02n.aimsbackend.exception.AimsOtpException;
import com.cuong02n.aimsbackend.service.IOtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

@Service
@EnableScheduling
public class OtpService implements IOtpService {

    public static final String OTP_CHAR = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
    @Value("${aims.otp.register.length}")
    public final int OTP_REGISTER_LENGTH = 6;
    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    // cuong02n@gmail.com - 78YD90-174123912833 ---
    private final ConcurrentHashMap<String, String> registerOtpAndCreateTime = new ConcurrentHashMap<>();
    @Value("${aims.otp.register.exp-millis}")
    private long REGISTER_OTP_EXP;

    @Scheduled(cron = "0 */2 * * * *")
    private void cleanRegisterOtpMap() {
        registerOtpAndCreateTime.forEach((s, s2) -> {
            if (Long.parseLong(s2.substring(OTP_REGISTER_LENGTH + 1)) < System.currentTimeMillis()) {
                registerOtpAndCreateTime.remove(s);
            }
        });
    }

    public String generateRegisterOtp(String email) {
        StringBuilder otpBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < OTP_REGISTER_LENGTH; i++) {
            otpBuilder.append(OTP_CHAR.charAt(random.nextInt(OTP_CHAR.length())));
        }
        String returnValue = otpBuilder.toString();

        // push to active OTP map
        registerOtpAndCreateTime.put(email, otpBuilder.append('-').append(System.currentTimeMillis()).toString());
        return returnValue;
    }

    public void checkRegisterOtp(String email, String otp) {
        log.info("ALL {}",registerOtpAndCreateTime);
        String otp$createTime = registerOtpAndCreateTime.get(email);
        if (otp$createTime == null) {
            throw new AimsOtpException("Not found otp");
        }

        String trueOtp = otp$createTime.substring(0, OTP_REGISTER_LENGTH);
        if (!otp.equals(trueOtp)) {
            throw new AimsOtpException(otp, trueOtp);
        }
        // not Expired;
        if (Long.parseLong(otp$createTime.substring(OTP_REGISTER_LENGTH + 1)) + REGISTER_OTP_EXP < System.currentTimeMillis()) {
            throw new AimsOtpException("EXPIRED");
        }
    }


}

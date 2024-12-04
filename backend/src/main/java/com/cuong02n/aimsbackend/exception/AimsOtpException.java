package com.cuong02n.aimsbackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AimsOtpException extends RuntimeException{
    private static final Logger log = LoggerFactory.getLogger(AimsOtpException.class);

    public AimsOtpException(String inputOtp, String trueOtp) {
        super(inputOtp);
        log.info("Input OTP: {}, True OTP: {}", inputOtp, trueOtp);
    }

    public AimsOtpException(String message){
        super(message);
        log.info(message);
    }

}

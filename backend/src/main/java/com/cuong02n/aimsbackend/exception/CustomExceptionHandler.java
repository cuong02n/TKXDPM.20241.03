package com.cuong02n.aimsbackend.exception;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleValidationException(MethodArgumentNotValidException ex) {
        return BaseResponse.error(Arrays.toString(Arrays.stream(ex.getDetailMessageArguments()).filter(e -> !((String) e).isEmpty()).toArray()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistException.class)
    public BaseResponse handleUserExistException(UserExistException ex) {
        return BaseResponse.error(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GeneralException.class)
    public BaseResponse handleGeneralException(GeneralException ex) {
        return BaseResponse.error(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return BaseResponse.error(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AimsOtpException.class)
    public BaseResponse handleOtpException(AimsOtpException ex) {
        return BaseResponse.error(ex.getMessage());
    }

}

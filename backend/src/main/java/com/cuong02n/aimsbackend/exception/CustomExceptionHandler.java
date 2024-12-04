package com.cuong02n.aimsbackend.exception;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@RequiredArgsConstructor
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
}

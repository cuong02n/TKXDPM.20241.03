package com.cuong02n.aimsbackend.model.dto.response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class BaseResponse {
    int error = 0;
    Object message;
    JsonElement data;

    public BaseResponse(Object entity) {
        if (entity == null) data = null;
        data = new Gson().toJsonTree(entity);
    }

    public BaseResponse(int error, Object message) {
        this.error = error;
        this.message = message;
        this.data = null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private static <T> ResponseEntity<?> createResponseEntity(Object entity, int statusCode) {
        return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(new BaseResponse(entity));
    }

    public static ResponseEntity<?> ok(Object entity) {
        return createResponseEntity(entity, 200);
    }

    public static BaseResponse error(Object message) {
        return new BaseResponse(1, message);
    }
}
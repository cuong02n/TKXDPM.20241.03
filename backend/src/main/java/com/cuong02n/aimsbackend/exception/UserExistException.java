package com.cuong02n.aimsbackend.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(String email) {
        super("User activated with email before: " + email);
    }
}

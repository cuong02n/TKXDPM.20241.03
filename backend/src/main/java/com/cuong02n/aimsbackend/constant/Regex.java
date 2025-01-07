package com.cuong02n.aimsbackend.constant;

public class Regex {
    public static final String REGEX_CHECK_PASSWORD = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String REGEX_CHECK_NAME="^^[a-zA-Z][a-zA-Z0-9]{6,}$";
}

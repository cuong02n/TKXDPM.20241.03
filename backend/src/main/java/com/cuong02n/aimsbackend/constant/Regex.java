package com.cuong02n.aimsbackend.constant;

public class Regex {
    public static final String REGEX_CHECK_PASSWORD = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String REGEX_CHECK_NAME="^^[a-zA-Z][a-zA-Z0-9]{6,}$";
    public static final String REGEX_CHECK_PHONE = "^0(?:\\d{9}|\\d[.-/]\\d{2}[.-/]\\d{2}[.-/]\\d{2}[.-/]\\d{2})$";
    public static final String REGEX_CHECK_ADDRESS = "^[a-zA-Z0-9/]{1,100}$";
}

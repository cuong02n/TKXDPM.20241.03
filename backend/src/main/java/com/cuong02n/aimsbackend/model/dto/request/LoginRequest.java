package com.cuong02n.aimsbackend.model.dto.request;

import com.cuong02n.aimsbackend.constant.Regex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "Not a valid email")
    String email;

    @Pattern(regexp = Regex.REGEX_CHECK_PASSWORD, message = "Password must contains: a-z, A-Z, 0-9, at least 8 characters, 1 upper case.")
    String password;
}

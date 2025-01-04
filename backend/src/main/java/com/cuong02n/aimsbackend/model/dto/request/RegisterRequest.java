package com.cuong02n.aimsbackend.model.dto.request;

import com.cuong02n.aimsbackend.constant.Regex;
import com.cuong02n.aimsbackend.model.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Valid
public class RegisterRequest {
    @Email(message = "Not a valid email")
    String email;

    @Pattern(regexp = Regex.REGEX_CHECK_NAME, message = "Username must contains: a-z, A-Z, 0-9.")
    @Size(min = 6, message = "Username must be at least 6 characters long")
    String name;

    @Pattern(regexp = Regex.REGEX_CHECK_PASSWORD, message = "Password must contains: a-z, A-Z, 0-9, 1 number, 1 upper case.")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;
}

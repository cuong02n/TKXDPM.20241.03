package com.cuong02n.aimsbackend.model.dto.request;

import com.cuong02n.aimsbackend.constant.Regex;
import com.cuong02n.aimsbackend.model.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Valid
public class RegisterRequest {
    @Email(message = "Not a valid email")
    String email;

    @Pattern(regexp = Regex.REGEX_CHECK_NAME, message = "Username must contains: a-z, A-Z, 0-9. At least 6 character")
    String name;

    @Pattern(regexp = Regex.REGEX_CHECK_PASSWORD, message = "Password must contains: a-z, A-Z, 0-9, at least 8 characters, 1 number, 1 upper case.")
    String password;
}

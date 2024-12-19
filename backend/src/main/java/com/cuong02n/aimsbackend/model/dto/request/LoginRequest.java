package com.cuong02n.aimsbackend.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
<<<<<<< HEAD
    @Email(message = "Not a valid email")
=======
    @Email
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
    String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must contains: a-z, A-Z, 0-9, at least 8 characters, 1 upper case.")
    String password;
}

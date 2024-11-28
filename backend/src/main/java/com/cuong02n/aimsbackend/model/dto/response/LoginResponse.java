package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String jwt;
    private User.Role role;
    private long expired;
}

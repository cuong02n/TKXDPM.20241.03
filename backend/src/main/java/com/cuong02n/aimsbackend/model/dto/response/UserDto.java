package com.cuong02n.aimsbackend.model.dto.response;

import com.cuong02n.aimsbackend.model.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String name;
    private User.Role role;
    private boolean active;
}

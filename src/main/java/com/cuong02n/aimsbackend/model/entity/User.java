package com.cuong02n.aimsbackend.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;

    class Role{
        private String role;
    }
}

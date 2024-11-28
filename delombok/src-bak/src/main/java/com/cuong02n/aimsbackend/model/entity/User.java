package com.cuong02n.aimsbackend.model.entity;

import lombok.Builder;

@Builder
public class User {

    String id;
    String name;
    String email;

    String phone;
    String password;
    String role;

}

package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface IJwtService {
    String extractUsername(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);

    String generateToken(User user);
}

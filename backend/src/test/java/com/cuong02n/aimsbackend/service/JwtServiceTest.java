package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private JwtService jwtService;


    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(User.Role.CUSTOMER);

        String token = jwtService.generateToken(user);

        assertNotNull(token);
    }

    @Test
    public void testIsTokenValid() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(User.Role.CUSTOMER);

        String token = jwtService.generateToken(user);
        boolean isValid = jwtService.isTokenValid(token, user);

        assertTrue(isValid);
    }

    @Test
    public void testExtractUsername() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(User.Role.CUSTOMER);

        String token = jwtService.generateToken(user);
        String username = jwtService.extractUsername(token);

        assertEquals(user.getEmail(), username);
    }
}
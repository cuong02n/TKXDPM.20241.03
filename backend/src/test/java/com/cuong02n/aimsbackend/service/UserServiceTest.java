package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.exception.UserExistException;
import com.cuong02n.aimsbackend.model.dto.request.LoginRequest;
import com.cuong02n.aimsbackend.model.dto.request.RegisterRequest;
import com.cuong02n.aimsbackend.model.dto.response.LoginResponse;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OtpService otpService;

    @Mock
    private EmailService emailService;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setName("Test User");

        when(userRepository.existsByEmailAndActiveTrue(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        userService.register(request);

        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendRegisterEmail(eq(request.getEmail()), eq(request.getName()), anyString());
    }

    @Test
    public void testVerifyRegister() {
        String email = "test@example.com";
        String otp = "123456";

        User user = new User();
        user.setEmail(email);
        user.setActive(false);

        when(userRepository.findById(email)).thenReturn(Optional.of(user));

        userService.verifyRegister(email, otp);

        assertTrue(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLogin() {
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);

        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        LoginResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    public void testLoginWithWrongPassword() {
        String email = "test@example.com";
        String password = "wrongPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("correctPassword"));

        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            userService.login(request);
        });

        assertEquals("Wrong password", exception.getMessage());
    }

    @Test
    public void testLoginWithInactiveUser() {
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(false);

        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            userService.login(request);
        });

        assertEquals("You account is not activated", exception.getMessage());
    }

    @Test
    public void testUserExist() {
        String email = "test@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.userExist(email);

        assertTrue(result);
    }

    @Test
    public void testUserActivated() {
        String email = "test@example.com";

        when(userRepository.existsByEmailAndActiveTrue(email)).thenReturn(true);

        boolean result = userService.userActivated(email);

        assertTrue(result);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "test@example.com";

        User user = new User();
        user.setEmail(username);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(username);

        assertEquals(user, result);
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        String username = "test@example.com";

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        UserDetails result = userService.loadUserByUsername(username);

        assertNull(result);
    }
}
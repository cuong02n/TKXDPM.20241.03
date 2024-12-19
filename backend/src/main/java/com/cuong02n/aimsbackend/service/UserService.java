package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.exception.UserExistException;
import com.cuong02n.aimsbackend.model.dto.request.LoginRequest;
import com.cuong02n.aimsbackend.model.dto.request.RegisterRequest;
import com.cuong02n.aimsbackend.model.dto.response.LoginResponse;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.ProductCartRepository;
import com.cuong02n.aimsbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    final UserRepository userRepository;
    final OtpService otpService;
    final EmailService emailService;
    final JwtService jwtService;
    final HttpServletRequest httpServletRequest;
    final PasswordEncoder passwordEncoder;
    final ProductCartRepository productCartRepository;


    public boolean userExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean userActivated(String email) {
        return userRepository.existsByEmailAndActiveTrue(email);
    }

    public void register(RegisterRequest request) {
        if (userActivated(request.getEmail())) {
            // check user exist and some logic for preventing spam
            throw new UserExistException(request.getEmail());
        }
        // exist but not activated
        if (userExist(request.getEmail())) {
            // todo
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .active(false)
                .role(User.Role.CUSTOMER)
                .build();
        String otp = otpService.generateRegisterOtp(request.getEmail());
        emailService.sendRegisterEmail(request.getEmail(), request.getName(), otp);
        userRepository.save(user);
    }

    public void verifyRegister(String email, String otp) {
        User user = userRepository.findById(email).orElseThrow();
        if (user.isEnabled()) {
            throw new UserExistException(email);
        }

        otpService.checkRegisterOtp(email, otp);
        user.activate();
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getEmail()).orElseThrow(() -> new GeneralException("Wrong password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GeneralException("Wrong password");
        }
        if (!user.isEnabled()) {
            throw new GeneralException("You account is not activated");
        }
        return new LoginResponse(jwtService.generateToken(user), user.getRole(), (long) httpServletRequest.getAttribute("expired-jwt"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElse(null);
    }
}

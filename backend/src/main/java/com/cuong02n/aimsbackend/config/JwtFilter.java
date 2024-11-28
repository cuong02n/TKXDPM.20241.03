package com.cuong02n.aimsbackend.config;

import com.cuong02n.aimsbackend.service.JwtService;
import com.cuong02n.aimsbackend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.cuong02n.aimsbackend.config.SecurityConfig.WHITE_LIST;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    final AntPathMatcher matcher = new AntPathMatcher();
    final UserService userService;
    final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            String jwt = authHeader.substring(7);
            String email = jwtService.extractUsername(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(email);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            request.setAttribute("email", email);
            request.setAttribute("username", email);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.stream(WHITE_LIST).anyMatch(pattern -> matcher.match(pattern, requestURI));
    }
}

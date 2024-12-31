package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.dto.response.UserDto;
import com.cuong02n.aimsbackend.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    final IUserService userService;
    final HttpServletRequest request;
    final ModelMapper modelMapper;

    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {
        return BaseResponse.ok(modelMapper.map(request.getAttribute("user"), UserDto.class));
    }
}


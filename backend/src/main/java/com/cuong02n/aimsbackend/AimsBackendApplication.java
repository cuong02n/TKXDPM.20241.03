package com.cuong02n.aimsbackend;

import com.cuong02n.aimsbackend.repository.UserRepository;
import com.cuong02n.aimsbackend.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Configuration
public class AimsBackendApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        var ctx = SpringApplication.run(AimsBackendApplication.class, args);
    }


}

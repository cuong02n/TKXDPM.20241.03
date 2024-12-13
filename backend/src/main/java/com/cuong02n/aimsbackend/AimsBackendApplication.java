package com.cuong02n.aimsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class AimsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AimsBackendApplication.class, args);
    }
}

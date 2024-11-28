package com.cuong02n.aimsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AimsBackendApplication {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(AimsBackendApplication.class, args);
    }

}

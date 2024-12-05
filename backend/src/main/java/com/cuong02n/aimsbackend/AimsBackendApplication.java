package com.cuong02n.aimsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class AimsBackendApplication {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/error")
    public String error() {
        return "Error";
    }

    @PostMapping("/test")
    public String test(@RequestParam("files") MultipartFile[] files, @RequestParam("star")int star) {
        return Arrays.toString(Arrays.stream(files).map(MultipartFile::getContentType).toArray());
    }

    public static void main(String[] args) {
        SpringApplication.run(AimsBackendApplication.class, args);
    }

}

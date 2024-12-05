package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ProductService productService;

    final HttpServletRequest request;

    @PostMapping("")
    public void review(
            @RequestParam(value = "media", required = false) List<MultipartFile> medias,
            @RequestParam("productId") String productId,
            @RequestParam("content") String content,
            @RequestParam("star") Integer star) {
        productService.review(medias, productId, content, star);
    }
}

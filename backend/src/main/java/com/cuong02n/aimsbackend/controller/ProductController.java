package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;
    private final HttpServletRequest httpServletRequest;


    @GetMapping("")
    public ResponseEntity<?> getProduct(@RequestParam String productId) {
        return BaseResponse.ok(productService.getProduct(productId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct() {
        return BaseResponse.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct() {
        return BaseResponse.ok(null);
    }

    @GetMapping("/wish-list")
    public ResponseEntity<?> getWishList() {
        return BaseResponse.ok(productService.getWishList((User) httpServletRequest.getAttribute("user")));
    }

    @PostMapping("/wish-list")
    public ResponseEntity<?> addWishList(@RequestParam Long productId) {
        productService.addWishList((User) (httpServletRequest.getAttribute("user")), productId);
        return BaseResponse.ok(null);
    }
}

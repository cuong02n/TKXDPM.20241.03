package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    public final ProductService productService;


    @GetMapping("")
    public ResponseEntity<?> getProduct(@RequestParam String productId){
        return BaseResponse.ok(productService.getProduct(productId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct(){
        return BaseResponse.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(){
        return BaseResponse.ok(null);
    }
}

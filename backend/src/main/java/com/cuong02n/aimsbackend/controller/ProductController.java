package com.cuong02n.aimsbackend.controller;

import com.cuong02n.aimsbackend.model.dto.response.BaseResponse;
import com.cuong02n.aimsbackend.model.dto.response.FavoriteProductUserDto;
import com.cuong02n.aimsbackend.model.dto.response.ProductDto;
import com.cuong02n.aimsbackend.model.entity.Product;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.service.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final IProductService productService;
    private final HttpServletRequest httpServletRequest;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<?> getProduct(@RequestParam long productId) {
        return BaseResponse.ok(
                modelMapper.map(productService.getProduct(productId), ProductDto.class)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct() {
        return BaseResponse.ok(
                productService.getAllProducts()
                        .stream().map(p -> modelMapper.map(p, Product.class))
                        .toList()
        );
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam("query") String query) {
        return productService.searchProducts(query);
    }

    @GetMapping("/wish-list")
    public ResponseEntity<?> getWishList() {
        return BaseResponse.ok(
                productService.getWishList((User) httpServletRequest.getAttribute("user"))
                        .stream()
                        .map(f -> modelMapper.map(f, FavoriteProductUserDto.class))
                        .toList()
        );
    }

    @PostMapping("/wish-list")
    public ResponseEntity<?> addWishList(@RequestParam Long productId) {
        productService.addWishList((User) (httpServletRequest.getAttribute("user")), productId);
        return BaseResponse.ok(null);
    }
}

package com.cuong02n.aimsbackend.service.impl;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.ProductCartRepository;
import com.cuong02n.aimsbackend.repository.ProductRepository;
import com.cuong02n.aimsbackend.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final ProductCartRepository productCartRepository;
    private final ProductRepository productRepository;
    public List<ProductCart> getUserCart(User user){
        return productCartRepository.findAllByKey_UserEmail(user.getEmail());
    }

    public void addToCart(User user, long productId, int quantity) {
        //check exist
        checkProductExistInCart(user.getEmail(), productId);

        ProductCart productCart = new ProductCart();
        productCart.setQuantity(quantity);
        productCart.setUser(user);
        productCart.setProduct(productRepository.findById(productId).orElseThrow());
        productCart.setKey(new ProductCart.ProductCartKey(productId,user.getEmail()));
        productCartRepository.save(productCart);
    }

    private void checkProductExistInCart(String useremail, long productId) {
        if (productCartRepository.existsByKey_UserEmailAndKey_ProductId(useremail, productId)) {
            throw new GeneralException("This product: %d is already in the cart".formatted(productId));
        }
    }
}

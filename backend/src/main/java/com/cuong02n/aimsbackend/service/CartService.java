package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.ProductCart;
import com.cuong02n.aimsbackend.model.entity.User;
import com.cuong02n.aimsbackend.repository.ProductCartRepository;
import com.cuong02n.aimsbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductCartRepository productCartRepository;

    public void addToCart(User user, long productId, int quantity) {
        //check exist
        checkProductExistInCart(user,productId);

        ProductCart productCart = new ProductCart();
        productCart.setKey(new ProductCart.ProductCartKey(productId,user.getEmail()));
        productCart.setQuantity(quantity);
        productCartRepository.save(productCart);
    }

    private void checkProductExistInCart(User user, long productId) {
        if(productCartRepository.existsByKey_UserEmailAndKey_ProductId(user.getEmail(),productId)){
            throw new GeneralException("This product: %d is already in the cart".formatted(productId));
        }
    }
}

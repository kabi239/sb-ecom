package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.payload.CartDTO;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);
}

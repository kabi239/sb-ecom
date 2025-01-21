package com.ecommerce.sb_ecom.repositories;

import com.ecommerce.sb_ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.email=?1") //here ?1 means first parameter and and here we are defining our own query
    Cart findCartByEmail(String email);
}

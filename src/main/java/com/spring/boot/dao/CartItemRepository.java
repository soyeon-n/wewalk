package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Cart;
import com.spring.boot.model.CartItem;
import com.spring.boot.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	void deleteByProductAndCart(Product product, Cart cart);
}

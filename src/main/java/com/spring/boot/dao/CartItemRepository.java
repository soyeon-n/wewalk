package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Cart;
import com.spring.boot.model.CartItem;
import com.spring.boot.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteByProductAndCart(Product product, Cart cart);

	
	//cartid 와 product 로 중복되어 담긴 상품이 있는 지 검사한다 
	Optional<CartItem> findByCartAndProduct(Cart cart,Product product);
	//And (A and B)
	//findBySubjectAndContent(String subject, String content)	


}

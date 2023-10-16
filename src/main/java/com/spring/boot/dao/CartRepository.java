package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUserId(Long user_id);
	

}

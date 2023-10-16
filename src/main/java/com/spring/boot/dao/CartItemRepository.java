package com.spring.boot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}

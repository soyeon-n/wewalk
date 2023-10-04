package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.boot.dao.CartItemRepository;
import com.spring.boot.dao.CartRepository;
import com.spring.boot.dao.ProductRepository;
import com.spring.boot.model.Cart;
import com.spring.boot.model.CartItem;
import com.spring.boot.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

	private final CartItemRepository cartItemRepository;
	//private final ProductRepository productRepository;
	//private final CartRepository cartRepository;
	
	public List<Product> getCartItemList(Long cart_id){
		
		List<Product> lists = new ArrayList<Product>();
		

		
		return lists;
		
	}

	public CartItem getCartItem(Long cartId){
		
		
		CartItem fail = new CartItem();
		fail.setId(111L);
		fail.setCount(999);
		
		
		Optional<CartItem> cartitem = cartItemRepository.findById(cartId);
		
		
		
		if(cartitem.isPresent()) {
		return cartitem.get();
		}else {
			return fail;
		}
		
	}
	
	
}

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
	private final ProductRepository productRepository;
	//private final CartRepository cartRepository;
	
	public List<Product> getCartItemList(Long cart_id){
		
		List<Product> lists = new ArrayList<Product>();
		

		return lists;
		
	}

	public void updateCartItemCount(Long cartItemId,int count) {
		
		Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
		CartItem item;
		if(cartItem.isPresent()) {
			item = cartItem.get();
			
			item.setCount(count);
			
			cartItemRepository.save(item);
		}
	}

	public void deleteCartItem(Long cartItemId) {
		
		cartItemRepository.deleteById(cartItemId);
		
	}
	
}

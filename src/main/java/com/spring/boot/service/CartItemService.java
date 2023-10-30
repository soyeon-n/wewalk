package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.dao.CartItemRepository;
import com.spring.boot.dao.CartRepository;
import com.spring.boot.dao.ProductRepository;
import com.spring.boot.dto.ItemDataForm;
import com.spring.boot.dto.PaymentDataForm;
import com.spring.boot.model.Cart;
import com.spring.boot.model.CartItem;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	
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
	
	@Transactional
	public void deleteCartItem(Long cartItemId) {
		
		cartItemRepository.deleteById(cartItemId);
		
	}

	@Transactional
	public void deleteBuyItems(PaymentDataForm paymentDateForm, SiteUser user) {
		
		List<ItemDataForm> lists = paymentDateForm.getItemIds();
		Cart cart = cartRepository.findByUserId(user.getId());
		
		for(ItemDataForm data : lists) {
			Product product = productRepository.findById(data.getId()).get();
			
			cartItemRepository.deleteByProductAndCart(product, cart);
		}
		
	}
	

	
	//은별 장바구니에 담아버리기 
	public void addCartItem(Product product,Integer count,Cart cart) {
		
		CartItem cartItem = new CartItem();
		
		cartItem.setProduct(product);//물건번호
		cartItem.setCount(count);//갯수 넣기 
		cartItem.setCart(cart);
		//siteuser 정보도 넣어야 하는데 ??? 
		cartItemRepository.save(cartItem);
		
	}
	//장바구니에 내가담으려는 product가 있는지 검색
	public boolean searchProduct(Product product,Cart cartId) {
		//optional 해야 할것같음 
		
		Optional<CartItem> op = cartItemRepository.findByCartAndProduct(cartId, product);
		
		if(op.isPresent()) {
			return true;
		}else {
			return false;
		}
		
		
		
	}
	
	

}

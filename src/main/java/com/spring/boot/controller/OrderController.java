package com.spring.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.dto.PaymentDataForm;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.CartItemService;
import com.spring.boot.service.CartService;
import com.spring.boot.service.OrderListService;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/order")
@RequiredArgsConstructor
@Controller
public class OrderController {

	private final CartService cartService;
	private final CartItemService cartItemService;
	private final UserService userService;
	private final ProductService productService;
	private final OrderListService orderListService;

	
	@GetMapping("/cart")
	public String cart(Model model ,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		//시큐리티 로그인된 사용자의 cartItem(장바구니목록)가져와 장바구니페이지로 이동
		
		List<Product> productList = cartService.getProductList(principalDetails.getUsername()); //로그인할때id 계정명
		
		model.addAttribute("cartItemList",cartService.getCartItemList(principalDetails.getUsername()));
		model.addAttribute("productList", productList);
		
		return "cart";
	}
	
	@PostMapping("/cartItemUpdate")
	public ResponseEntity<String> cartItemUpdate(@RequestParam Long cartItemId, @RequestParam int count) {
	    cartItemService.updateCartItemCount(cartItemId, count);
	    return ResponseEntity.ok("Cart item updated successfully");
	    
	}
	
	@PostMapping("/cartItemDelete")
	public ResponseEntity<String> cartItemDelete(@RequestParam Long cartItemId) {
		cartItemService.deleteCartItem(cartItemId);
		return ResponseEntity.ok("Cart item deleted successfully");
	}
	
	@PostMapping("/cartItemSelDelete")
	public ResponseEntity<String> cartItemDelete(@RequestParam(value="cartItemIds[]") List<Long> cartItemIds) {

        for (Long itemId : cartItemIds) {
            cartItemService.deleteCartItem(itemId);
        }
        return ResponseEntity.ok("Selected items deleted successfully");
	    
		
	}
	
	
	@GetMapping("/detail")
	public String payDetail(Model model,@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestParam(name = "selectedProducts") String selectedProductsJSON) {
		
		
		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
		
		//JSON데이터 역직렬화 
		List<Map<String, Integer>> selectedProducts = new ArrayList<>();
			try {
			    ObjectMapper objectMapper = new ObjectMapper();
			    selectedProducts = objectMapper.readValue(selectedProductsJSON, new TypeReference<List<Map<String, Integer>>>(){});
			} catch (JsonProcessingException e) {
				
			}
		
		Map<Product, Integer> productList = new HashMap<>();	
		
		for(Map<String,Integer> selectProduct : selectedProducts) {
			
			Integer productId = selectProduct.get("productId");
			Integer quantity = selectProduct.get("quantity");
			
			Product product = productService.getProductById(productId);
			
			productList.put(product, quantity);
			
		}
		
		
		
		model.addAttribute("SiteUser",user);
		model.addAttribute("productList", productList);
		
		return "order_detail";
	}

	//결제정보 저장
	@PostMapping("/checkout")
	public ResponseEntity<String> saveOrder(@RequestBody PaymentDataForm paymentDataForm,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
		
		orderListService.saveOrderHistory(paymentDataForm,user);
		

		return new ResponseEntity<>("Order saved successfully", HttpStatus.OK);
	}
	
	
	
}

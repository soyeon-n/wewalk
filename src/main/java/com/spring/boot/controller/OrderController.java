package com.spring.boot.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.boot.model.CartItem;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.CartItemService;
import com.spring.boot.service.CartService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/order")
@RequiredArgsConstructor
@Controller
public class OrderController {

	private final CartService cartService;
	private final CartItemService cartItemService;
	private final UserService userService;
	
	@GetMapping("/testsql")
	@ResponseBody
	public String sqlq(Authentication authentication) {
		
		String email="";
	
		email = authentication.getName(); //email가져옴
		//dghs1233@naver.com
		
		//SiteUser user = userService.getUserByEmail(email);
		
		return "getName: " +email;
	}
	
	
	@GetMapping("/cart")
	public String cart(Model model ,Authentication authentication) {
		
		//시큐리티 로그인된 사용자의 cartItem(장바구니목록)가져와 장바구니페이지로 이동
		
		List<Product> productList = cartService.getProductList(authentication.getName());
		
		model.addAttribute("cartItemList",cartService.getCartItemList(authentication.getName()));
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
	
	@GetMapping("/test")
	public String text(Model model) {
		
		
		
		
		return "order_test";
	}
	
	@PostMapping("/detail")
	public String payDetail(Model model,
				            @RequestParam("productName") String productName,
				            @RequestParam("count") int count,
				            @RequestParam("price") int price,
				            Authentication authentication) {
		
		String email = authentication.getName(); //email가져옴

		SiteUser user = userService.getUserByEmail(authentication.getName());
		
		model.addAttribute("productName", productName);
		model.addAttribute("count", count);
		model.addAttribute("price", price);
		model.addAttribute("SiteUser",user);
		//view에는앞에서 주문한 상품 정보 받아서 뿌려주고
		//주문데이터 입력받음
		
		return "order_detail";
	}
	
	@PostMapping("/checkout")
	public String payRequest(Model model) {
		
		//이곳에서 api로 결제를 시도 성공시 result 실패시 결제시도 전으로
		

		return "order_result";
	}
	
	
}

package com.spring.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.boot.model.Cart;
import com.spring.boot.model.CartItem;
import com.spring.boot.model.Product;
import com.spring.boot.service.CartItemService;
import com.spring.boot.service.CartService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/order")
@RequiredArgsConstructor
@Controller
public class OrderController {

	private final CartService cartService;
	private final CartItemService cartItemService;
	
	@GetMapping("/testsql")
	@ResponseBody
	public int sqlq() {
		
		int str;
	
		CartItem cart = cartItemService.getCartItem(2L);
		

		str = cart.getCount();
		
		return str;
	}
	
	@GetMapping("/cart")
	public String cart(Model model) {
		
		//해당유저의 id인데 세션의 id로 수정할것
		Long cartId = 2L;
		
	
		List<Product> productList = cartService.getProductList(cartId);
		
		model.addAttribute("productList", productList);
		
		return "cart";
	}
	
	@GetMapping("/test")
	public String text(Model model) {
		
		
		
		
		return "order_test";
	}
	
	@PostMapping("/detail")
	public String payDetail(Model model,
				            @RequestParam("productName") String productName,
				            @RequestParam("count") int count,
				            @RequestParam("price") int price) {
		
		model.addAttribute("productName", productName);
		model.addAttribute("count", count);
		model.addAttribute("price", price);
		
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

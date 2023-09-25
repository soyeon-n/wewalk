package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequestMapping("/order")
@RequiredArgsConstructor
@Controller
public class OrderController {

	@GetMapping("/test")
	public String text(Model model) {
		
		//장바구니(추가한다면) or 상품상세페이지에서
		//물건 갯수 고르고 다음페이지
		
		
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

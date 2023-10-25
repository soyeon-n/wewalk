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
import com.spring.boot.dto.OrderResultForm;
import com.spring.boot.dto.PaymentDataForm;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.model.OrderList;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.CartItemService;
import com.spring.boot.service.CartService;
import com.spring.boot.service.OrderListService;
import com.spring.boot.service.PayService;
import com.spring.boot.service.PointService;
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
	private final PointService pointService;
	private final PayService payService;
	
	@GetMapping("/cart")
	public String cart(Model model ,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		//시큐리티 로그인된 사용자의 cartItem(장바구니목록)가져와 장바구니페이지로 이동
		if(principalDetails!=null) {
		List<Product> productList = cartService.getProductList(principalDetails.getUsername()); //로그인할때id 계정명
		
		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
		
		model.addAttribute("cartItemList",cartService.getCartItemList(principalDetails.getUsername()));
		model.addAttribute("productList", productList);
		model.addAttribute("user",user);
		return "cart";
		}
		//비로그인계정은 로그인으로
		return "redirect:/auth/login";
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
		List<Map<String, Long>> selectedProducts = new ArrayList<>();
			try {
			    ObjectMapper objectMapper = new ObjectMapper();
			    selectedProducts = objectMapper.readValue(selectedProductsJSON, new TypeReference<List<Map<String, Long>>>(){});
			} catch (JsonProcessingException e) {
				
			}
		
		Map<Product, Long> productList = new HashMap<>();	
		
		for(Map<String,Long> selectProduct : selectedProducts) {
			
			Long productId = selectProduct.get("productId");
			Long quantity = selectProduct.get("quantity");
			
			Product product = productService.getProductById(productId);
			
			productList.put(product, quantity);
			
		}
		
		model.addAttribute("SiteUser",user);
		model.addAttribute("productList", productList);
		
		return "order_detail";
	}

	//결제정보 저장

	@PostMapping("/checkout")
	@ResponseBody
	public Map<String, Object> saveOrder(@RequestBody PaymentDataForm paymentDataForm,
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		
		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
		
		//구매한상품들 재고 감소
		productService.updateProductStock(paymentDataForm.getItemIds());
		
		//orderlist(구매내역테이블 저장)
		List<OrderList> orderLists = orderListService.saveOrderHistory(paymentDataForm,user);
		
		//장바구니에서 삭제
		cartItemService.deleteBuyItems(paymentDataForm, user);
		
		int point = paymentDataForm.getPointPay();
		int payMoney = paymentDataForm.getPayMoney();
		
		//포인트사용시 포인트사용내역저장
		if(point>0) {
			pointService.saveUsePointHistory(user, point, paymentDataForm.getName());
		}
		//페이머니 결제금액있을경우 사용내역저장
		if(payMoney>0) {
			payService.savePayHistory(user, payMoney, paymentDataForm.getName());
		}
		//구매등급,멤버쉽에따라 포인트적립
		String grade = user.getGrade();
		boolean membership = user.isMembership();
		int accumulate =1; //적립율 기본1,실버3,골드5,플래티넘8 , 멤버쉽가입시 추가10퍼센트 적립
		switch (grade) {
		case "P":	
			accumulate=8;
			break;
		case "G":	
			accumulate=5;
			break;
		case "S":	
			accumulate=3;
			break;
		}
		if(membership) {
			accumulate+=10;
		}
		
		//적립금,페이머니,등급조정
		int getPoint = userService.updateAfterOrder(user, point, payMoney, paymentDataForm.getPaid_amount() , accumulate);
		
		//포인트적립내역저장
		pointService.saveGetPointHistory(user, getPoint, paymentDataForm.getName());
		
		//주문번호,상품이름,이미지,판매자id,수량,가격 담아놓음
		List<OrderResultForm> paymentsData = productService.getResultForm(orderLists);
		
		
		Map<String, Object> response = new HashMap<>();
	    response.put("status", "success");
	    response.put("paymentsData", paymentsData);

		
		return response;
	}
	
	@PostMapping("/order_result")
	public String orderResult(@RequestParam("paymentsData") String paymentsDataJson, Model model) {
		
		try {
			
	        ObjectMapper objectMapper = new ObjectMapper();

	        List<OrderResultForm> paymentsData = objectMapper.readValue(paymentsDataJson, new TypeReference<List<OrderResultForm>>() {});

	        model.addAttribute("paymentsData", paymentsData);

	        return "order_result";
	    }catch (Exception e) {
	    	return "errorpage";	
		}
	}
	

}

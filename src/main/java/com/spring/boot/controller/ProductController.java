package com.spring.boot.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.dto.ProductForm;
import com.spring.boot.dto.QuestionForm;
import com.spring.boot.model.Cart;
import com.spring.boot.model.Product;
import com.spring.boot.model.Question;
import com.spring.boot.model.Review;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.CartItemService;
import com.spring.boot.service.CartService;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.QuestionService;
import com.spring.boot.service.ReviewService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")//prefix
@RequiredArgsConstructor
@Controller
public class ProductController {
	
	//service 연결 
	private final ProductService productService;
	private final ReviewService reviewService;
	private final QuestionService questionService;
	private final CartItemService cartItemService;
	private final CartService cartService;
	private final UserService userService;
	
	//전체상품 조회하는 메소드 =main?
	@RequestMapping("/list")
	public String list(Model model, @PageableDefault Pageable pageable) {
		
		Page<Product> paging = productService.getTotalLists(pageable);
		model.addAttribute("paging",paging);//request.setattribute 와 같음 html 로
		
		//return "mainList";//html 인식
		//return "search";//이게 검색결과 뿌리는 창 
		return "AcoTest";//아코디언메뉴 테스트 
	}
	
	//상품을 눌렀을떄 상품번호에 따른 주소가 달라짐 
	//상품 눌렀을떄 상세주소로 이동하는 주소
	
	
	/*
	@RequestMapping("/detail/{productNo}")
	public String detail(Model model, @PathVariable("productNo") long productNo
			,ProductForm productForm , @PageableDefault Pageable pageable) {
		
		Product product = productService.getProductDetailByNo(productNo);
		
		model.addAttribute("product",product);
		
		
		//상품리뷰 페이징을 위한 값 넘김
		Product productnum = productService.getProductDetailByNo(productNo);
		
		Page<Review> paging = reviewService.getPnoReview(pageable, productnum);
		
		Page<Question> paging1 = questionService.getQuestionList(pageable, product);
		
		model.addAttribute("paging1",paging1);//qna문의하기의페이징 qna_list layout
		model.addAttribute("paging",paging);//상품리뷰의 페이징
		
		
		
		
		
		return "product_list";//html연결
		
		
	}*/
	
	//ajax paging 
	@GetMapping("/detail/{productNo}")
	public String detail(Model model, @PathVariable("productNo") long productNo
			,ProductForm productForm , @PageableDefault Pageable pageable,
			
			
			@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size, Model model1) {
		
		
		Product product = productService.getProductDetailByNo(productNo);
		
		model1.addAttribute("product",product);
		
		
		//상품리뷰 페이징을 위한 값 넘김
		Product productnum = productService.getProductDetailByNo(productNo);
		
		Page<Review> paging = reviewService.getPnoReview(pageable, productnum);
		
		Page<Question> paging1 = questionService.getQuestionList(pageable, product);
		
		//ajax 페이징을 위한 코딩 여기 추가 
		PageRequest pageable1 = PageRequest.of(page, size);
		Page<Question> entities = questionService.getQuestionList(pageable1, product);
	    model1.addAttribute("entities", entities);
		
		
		model1.addAttribute("paging1",paging1);//qna문의하기의페이징 qna_list layout
		model1.addAttribute("paging",paging);//상품리뷰의 페이징
		
		return "product_list";//html연결
		
	}
	
	//카트넣기 post 방식으로 값을받아옴 
	@PostMapping("/addCart")
	public ResponseEntity<String> addCart(Model model, @RequestParam long productNo, 
			@RequestParam Integer number ,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		//거기서 addCart 함수로 값 productNo 와 수량 number 가 오면 
		//db 에 갯수와 count productid 를 넣는다 
		
		Product product = productService.getProductDetailByNo(productNo);
		
		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
		
		Cart cart=cartService.getOneCart(user);//siteuser 를 넣어주면 cart 를 반환한다 
		
		cartItemService.addCartItem(product, number, cart);
		
		return ResponseEntity.ok("Cart item added successfully");
	}
	
	
	
	
		
	
	
	
	
	
	
	

}

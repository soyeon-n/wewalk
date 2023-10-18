package com.spring.boot.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.dto.ProductForm;
import com.spring.boot.model.Product;
import com.spring.boot.model.Review;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")//prefix
@RequiredArgsConstructor
@Controller
public class ProductController {
	
	//service 연결 
	private final ProductService productService;
	private final ReviewService reviewService;
	
	//전체상품 조회하는 메소드 =main?
	@RequestMapping("/list")
	public String list(Model model, @PageableDefault Pageable pageable) {
		
		Page<Product> paging = productService.getTotalLists(pageable);
		model.addAttribute("paging",paging);//request.setattribute 와 같음 html 로
		
		return "mainList";//html 인식
		
	}
	
	//상품을 눌렀을떄 상품번호에 따른 주소가 달라짐 
	//상품 눌렀을떄 상세주소로 이동하는 주소
	
	@RequestMapping("/detail/{productNo}")
	public String detail(Model model, @PathVariable("productNo") Integer productNo
			,ProductForm productForm , @PageableDefault Pageable pageable) {
		
		Product product = productService.getProductDetailByNo(productNo);
		
		model.addAttribute("product",product);
		
		
		//상품리뷰 페이징을 위한 값 넘김
		Product productnum = productService.getProductDetailByNo(productNo);
		
		Page<Review> paging = reviewService.getPnoReview(pageable, productnum);
		model.addAttribute("paging",paging);
		
		
		return "product_list";//html연결
		
		
	}
	
	
	
	//개별판매자id 를 입력하면 상품리스트  조회하는 주소
	//상품판매자 이름 누를시 상품판매자 마이페이지로 이동 
	
	
	
	
	
	

}

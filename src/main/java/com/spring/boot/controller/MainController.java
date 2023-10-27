package com.spring.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.spring.boot.dto.PageRequestDTO;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.service.OrderListService;
import com.spring.boot.service.ProductService;

import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/wewalk")
@RequiredArgsConstructor
@Controller
public class MainController {

	private final UserService userService;
	private final ProductService productService;
	private final OrderListService orderListService;
	private final SpringTemplateEngine templateEngine;

	
	@GetMapping("/main")
	public String mainPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		
		
		if(principalDetails != null) {
			SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
			
			model.addAttribute("user",user);
		
		}
		
		//판매량 상위 8개
    	Pageable top8 = PageRequest.of(0, 8);
    	
    	List<Long> top8SellingProductnos = orderListService.getTopNSellingProductnos(top8);
		
		//프로덕트에서 검색해서 가장많이팔린거 8개 리스트 들고감
		List<Product> productListTop8 = productService.getTopNSellingProducts(top8SellingProductnos);
		model.addAttribute("productListTop8", productListTop8);
		
		return "mainPage";
	}
	
	
	//메인에서 검색시이곳으로 keyword가 검색어
    @GetMapping("/search")
    public String search( //@PageableDefault Pageable pageable 이미 pageable에 대한 설정 하고 왔음 
    		@RequestParam(value = "sort", required = false) String sort, 
			@ModelAttribute PageRequestDTO pageRequestDTO, Model model, 
    		@AuthenticationPrincipal PrincipalDetails principalDetails) {
        
    	String sortText = "";
    	if(principalDetails != null) {		
    		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
    		
    		model.addAttribute("user", user);
    	}
    	
    	model.addAttribute("paging", productService.getSearchList(pageRequestDTO, sort));
    	
    	if(sort != null && !sort.isEmpty()) {
    		if(sort.equals("newest")) {
    			sortText = "신상품순";
    		}else if(sort.equals("priceAsc")) {
    			sortText = "낮은 가격순";
    		}else if(sort.equals("priceDesc")) {
    			sortText = "높은 가격순";
    		}else if(sort.equals("category")) {
    			sortText = "카테고리순";
    		}
    		
            model.addAttribute("sort", sort);
            model.addAttribute("sortText", sortText);
        }else {
        	model.addAttribute("sort", "newest");
        	model.addAttribute("sortText", "신상품순");
        }
    	
    	if(pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().isEmpty()) {
    		model.addAttribute("keyword", pageRequestDTO.getKeyword());
    	}
        
    	return "search";
        
    }
    
    //베스트 상품 페이징
    @GetMapping("/searchBest")
    public String searchBest( //@PageableDefault Pageable pageable, 
    		@RequestParam(value = "sort", required = false) String sort, 
			@ModelAttribute PageRequestDTO pageRequestDTO, Model model, 
    		@AuthenticationPrincipal PrincipalDetails principalDetails) {
        
    	String sortText = "";
    	
    	if(principalDetails != null) {		
    		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
    		
    		model.addAttribute("user", user);
    	}
    	
    	//판매량 상위 20개
    	Pageable top20 = PageRequest.of(0, 20);
    	
    	List<Long> top20SellingProductnos = orderListService.getTopNSellingProductnos(top20);
    	List<Long> productnosTop20SellingNBoughtMoreThan3TimesBySameUser = orderListService
    					.getProductnosBoughtMoreThan3TimesBySameUser(top20SellingProductnos);
    	
    	model.addAttribute("paging", productService.getBestProducts(productnosTop20SellingNBoughtMoreThan3TimesBySameUser, 
    																pageRequestDTO, sort));
    	if(sort != null && !sort.isEmpty()) {
    		if(sort.equals("newest")) {
    			sortText = "신상품순";
    		}else if(sort.equals("priceAsc")) {
    			sortText = "낮은 가격순";
    		}else if(sort.equals("priceDesc")) {
    			sortText = "높은 가격순";
    		}else if(sort.equals("category")) {
    			sortText = "카테고리순";
    		}
    		
            model.addAttribute("sort", sort);
            model.addAttribute("sortText", sortText);
        }else {
        	model.addAttribute("sort", "newest");
        	model.addAttribute("sortText", "신상품순");
        }
    	
//    	if(pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().isEmpty()) {
//    		model.addAttribute("keyword", pageRequestDTO.getKeyword());
//    	}
        
    	return "searchBest";
        
    }
    
}

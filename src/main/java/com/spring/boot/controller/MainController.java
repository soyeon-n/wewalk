package com.spring.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final SpringTemplateEngine templateEngine;
	private final OrderListService orderListService;
	
	@GetMapping("/main")
	public String mainPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		
		
		if(principalDetails != null) {
			SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
			
			model.addAttribute("user",user);
		
		}
		
		//프로덕트에서 검색해서 가장많이팔린거 8개 리스트 들고감
		List<Product> productListTop8 = orderListService.getTop8SellingProducts();
		model.addAttribute("productListTop8",productListTop8);
		
		return "mainPage";
	}
	
	
	//메인에서 검색시이곳으로 keyword가 검색어
    @GetMapping("/search")
    public String Search(@PageableDefault Pageable pageable, 
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "sort", required = false) String sort, 
			@ModelAttribute PageRequestDTO pageRequestDTO, Model model, 
    		@AuthenticationPrincipal PrincipalDetails principalDetails) {
        
    	String sortText = "";
    	SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
    	
    	model.addAttribute("user", user);
    	model.addAttribute("paging", productService.getSearchList(pageRequestDTO, sort));
    	
    	if(sort != null && !sort.isEmpty()) {
    		if(sort.equals("newest")) {
    			sortText = "신상품순";
    		}else if(sort.equals("priceAsc")) {
    			sortText = "낮은 가격순";
    		}else if(sort.equals("priceDesc")) {
    			sortText = "높은 가격순";
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
    
}

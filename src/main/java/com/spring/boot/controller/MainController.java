package com.spring.boot.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.spring.boot.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/wewalk")
@RequiredArgsConstructor
@Controller
public class MainController {

	private final ProductService productService;
	private final SpringTemplateEngine templateEngine;
	
	@GetMapping("/main")
	public String mainPage( 
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		if(principalDetails != null) {
			String userName = principalDetails.getUsername();
			model.addAttribute("userName", userName);
		}
		
		return "mainPage";
	}
	
    @GetMapping("/search")
    public String Search(@PageableDefault Pageable pageable, 
    		@RequestParam(value = "sort", required = false) String sort, 
			@ModelAttribute PageRequestDTO pageRequestDTO, Model model, 
    		@AuthenticationPrincipal PrincipalDetails principalDetails) {
        
    	model.addAttribute("paging", productService.getSearchList(pageRequestDTO, sort));
    	model.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "search";
    }
	
    private String renderFragment(String template, Object payload) {
        Context context = new Context();
        context.setVariable("payload", payload);
        return templateEngine.process(template, context);
    }

}

package com.spring.boot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/wewalk")
@RequiredArgsConstructor
@Controller
public class MainController {

	private final UserService userService;
	
	@GetMapping("/main")
	public String mainPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		
		
		if(principalDetails != null) {
			SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
			
			model.addAttribute("user",user);
		
		}
		
		//프로덕트에서 검색해서 가장많이팔린거 8개 리스트 들고감 model.add
		
		return "mainPage";
	}
	
	
	//메인에서 검색시이곳으로 keyword가 검색어
    @GetMapping("/search")
    @ResponseBody
    public String Search(@RequestParam("keyword") String keyword, Model model) {
        
    	
    	
        return keyword;
    }
	

}

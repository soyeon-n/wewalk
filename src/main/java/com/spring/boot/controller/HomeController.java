package com.spring.boot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(principalDetails!=null) {		
			System.out.println(principalDetails.getEmail() + "/" + principalDetails.getUsername() + "/" + principalDetails.getPicture());
			model.addAttribute("email", principalDetails.getEmail());
			model.addAttribute("name", principalDetails.getUsername());
			model.addAttribute("picture", principalDetails.getPicture());
			
		}
		
		return "index";
	}
}

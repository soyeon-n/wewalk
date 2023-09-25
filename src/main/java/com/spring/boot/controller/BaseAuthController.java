package com.spring.boot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.boot.dto.SessionUser;
import com.spring.boot.service.LoginUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BaseAuthController {

	private final HttpSession httpSession;
	
	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) {
		
		if(user!=null) {
			model.addAttribute("email", user.getEmail());
			model.addAttribute("name", user.getName());
			model.addAttribute("picture", user.getPicture());
		}
		return "index";
	}
	
}

package com.spring.boot.controller;


import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.dto.UserCreateForm;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping("signup")
	public String signup(UserCreateForm userCreateForm) {
		
		return "signup_form";
		
	}
	
	@PostMapping("signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindResult) {
		
		//입력값 검증
		if(bindResult.hasErrors()) {
			return "signup_form";
		}
		
		if(!userCreateForm.getPassword1()
				.equals(userCreateForm.getPassword2()) ) {
			bindResult.rejectValue("password2", "passwordInCorrect",
					"2개의 패스워드가 일치하지 않습니다!");
			
			return "signup_form";
		}
		
		//입력값 DB에 넣으면서 검증
		try {
			
			userService.create(userCreateForm.getUserName(), 
					userCreateForm.getEmail(), userCreateForm.getPassword1());
		
		} catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			bindResult.reject("signupFailed", "이미 등록된 사용자입니다");
			
			return "signup_form";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			bindResult.reject("signupFailed", e.getMessage());
			
			return "signup_form";
			
		}
		return "redirect:/";
	}
	
	//login은 security가 처리하므로 post방식의 로그인 처리 메소드는 없어도 됨
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
}

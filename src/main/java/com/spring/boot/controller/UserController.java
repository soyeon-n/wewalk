package com.spring.boot.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.boot.dto.UserCreateForm;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping("signup")
	public String signup(UserCreateForm userCreateForm) {
		
		return "join";
		
	}
	
	
	@PostMapping("signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindResult) {
		
		//입력값 검증
		if(bindResult.hasErrors()) {
			bindResult.reject("signupFailed", "유효성 검증에 실패했습니다");
			return "join1";
			
		}
		
		//패스워드1과 2가 일치하는지 검증
		if(!userCreateForm.getPassword1()
				.equals(userCreateForm.getPassword2()) ) {
			bindResult.rejectValue("password2", "passwordInCorrect",
					"2개의 패스워드가 일치하지 않습니다!");
			
			return "join2";
		}
		
		//입력값 DB에 넣으면서 검증(서버사이드)
		try {
			
			//날짜 검증 및 날짜 형식으로 변환
			int year = Integer.parseInt(userCreateForm.getBirth_year());
		    int month = Integer.parseInt(userCreateForm.getBirth_month());
		    int day = Integer.parseInt(userCreateForm.getBirth_day());

		    LocalDate birthDate = LocalDate.of(year, month, day);
			
			userService.create(userCreateForm.getEmail(), userCreateForm.getUserName(), 
					 userCreateForm.getPassword1(), userCreateForm.getName(), userCreateForm.getTel(),
					 userCreateForm.getAddress(), userCreateForm.getDetailAddress(), birthDate);
		
		} catch (DateTimeException e) {
		    // 유효하지 않은 날짜
			e.printStackTrace();
			bindResult.reject("signupFailed", "유효하지 않은 날짜입니다");
			return "join3";
			
		} catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			bindResult.reject("signupFailed", "이미 등록된 사용자입니다");
			
			return "join4";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			bindResult.reject("signupFailed", e.getMessage());
			
			return "join5";
			
		}
		return "redirect:/user/login";
	}
	
	@GetMapping("/checkEmail")
    public Map<String, String> checkEmail(@RequestParam String email) {

        SiteUser user = userService.getUserByEmail(email);
        
        Map<String, String> result = new HashMap<>();
        
        if (user != null) {
            result.put("overlap", "fail");
        } else {
            result.put("overlap", "success");
        }
        return result;
    }
	
	@GetMapping("/checkUserName")
    public Map<String, String> checkUserName(@RequestParam String userName) {
        SiteUser user = userService.getUserByUserName(userName);
        Map<String, String> result = new HashMap<>();
        if (user != null) {
            result.put("overlap", "fail");
        } else {
            result.put("overlap", "success");
        }
        return result;
    }

	//login은 security가 처리하므로 post방식의 로그인 처리 메소드는 없어도 됨
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
}

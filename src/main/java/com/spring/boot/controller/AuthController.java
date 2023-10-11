package com.spring.boot.controller;

import java.time.DateTimeException;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.dto.UserCreateForm;
import com.spring.boot.model.UserRole;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

	private final HttpSession httpSession;
	private final UserService userService;
	
	
	@GetMapping("/signup")
	//@PreAuthorize("isAnonymous()") // 로그인되지 않은 사용자에게만 허용
	public String signup(UserCreateForm userCreateForm) {
		
		return "signup_form";
		
	}
	
	
	@PostMapping("/signup")
	//@PreAuthorize("isAnonymous()") // 로그인되지 않은 사용자에게만 허용
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindResult) {
		
		//입력값 검증
		if(bindResult.hasErrors()) {
			bindResult.reject("signupFailed", "유효성 검증에 실패했습니다");
			return "signup_form";
			
		}
		
		//패스워드1과 2가 일치하는지 검증
		if(!userCreateForm.getPassword1()
				.equals(userCreateForm.getPassword2()) ) {
			bindResult.rejectValue("password2", "passwordInCorrect",
					"2개의 패스워드가 일치하지 않습니다!");
			
			return "signup_form";
		}
		
		//입력값 DB에 넣으면서 검증(서버사이드)
		try {
			
			//날짜 검증 및 날짜 형식으로 변환
			int year = Integer.parseInt(userCreateForm.getBirthYear());
		    int month = Integer.parseInt(userCreateForm.getBirthMonth());
		    int day = Integer.parseInt(userCreateForm.getBirthDay());

		    LocalDate birthDate = LocalDate.of(year, month, day);
		    
		    UserRole role;
		    Resource resource = new ClassPathResource("static/images/flower-8173829_640.jpg");
		    String picture = "/images/flower-8173829_640.jpg";
		    
		    //이메일 주소가 admin@wewalkpay.com이면 role에 ADMIN을 주고 아니면 USER
			if(userCreateForm.getUserName() == "wewalkpay" || "wewalkpay".equals(userCreateForm.getUserName())) {
				role = UserRole.ADMIN;
			}else {
				role = UserRole.USER;
			}
			
		    //UserRole을 지정해서 넣어줘야 하고 거기에 추가로 UserCreateForm과 UserService, SiteUser에서의 데이터 입력 순서를 맞춰줘야 함
			userService.create(role, userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getUserName(), 
						userCreateForm.getName(), birthDate, userCreateForm.getPostcode(),
						userCreateForm.getAddress(), userCreateForm.getDetailAddress(), userCreateForm.getTel(), picture);
		
		} catch (DateTimeException e) {
		    // 유효하지 않은 날짜
			e.printStackTrace();
			bindResult.reject("signupFailed", "유효하지 않은 날짜입니다");
			return "signup_form";
			
		} catch (DataIntegrityViolationException e) {
			
			e.printStackTrace();
			bindResult.reject("signupFailed", "이미 등록된 사용자입니다");
			
			return "signup_form";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			bindResult.reject("signupFailed", e.getMessage());
			
			return "signup_form";
			
		}
		return "redirect:/auth/login";
	}
	
//	@GetMapping("/checkEmail")
//    public Map<String, String> checkEmail(@RequestParam String email) {
//
//        SiteUser user = userService.getUserByEmail(email);
//        
//        Map<String, String> result = new HashMap<>();
//        
//        if (user != null) {
//            result.put("overlap", "fail");
//        } else {
//            result.put("overlap", "success");
//        }
//        return result;
//    }
//	
//	@GetMapping("/checkUserName")
//    public Map<String, String> checkUserName(@RequestParam String userName) {
//        SiteUser user = userService.getUserByUserName(userName);
//        Map<String, String> result = new HashMap<>();
//        if (user != null) {
//            result.put("overlap", "fail");
//        } else {
//            result.put("overlap", "success");
//        }
//        return result;
//    }

	//login은 security가 처리하므로 post방식의 로그인 처리 메소드는 없어도 됨
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
}

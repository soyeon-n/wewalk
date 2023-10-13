package com.spring.boot.controller;

import java.security.Principal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exe.boardmine.question.QuestionForm;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.dto.SellerRequestForm;
import com.spring.boot.dto.UserCreateForm;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;
import com.spring.boot.service.FileService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final FileService fileService;

	//USER로 접근할 수 있는 페이지 관리할 예정
	@PreAuthorize("isAuthenticated")
	@PostMapping("/requestSeller")
//	public String questionCreate(@RequestParam String subject, @RequestParam String content) {
	//게시글 올릴 때 QuestionForm을 통해 검증하고 온 데이터가 BindingResult에 들어감
	//에러가 발생했을 시 
	public String requestSeller(@Valid SellerRequestForm sellerRequestForm, BindingResult bindResult, 
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindResult.hasErrors()) {
			return "request_seller";
		}
		
		//신청자 기입
		SiteUser siteUser = userService.getUser(principalDetails.getUser().getId());
		
		fileService.saveAttachments(multipartFileListMap)(questionForm.getSubject(), questionForm.getContent(), siteUser);
		
		int id = questionService.getMaxId();
		
		//return "redirect:/question/list";
		return String.format("redirect:/question/detail/%s", id);
		
	}
	
}

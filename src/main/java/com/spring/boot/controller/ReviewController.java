package com.spring.boot.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.dto.ReviewForm;
import com.spring.boot.model.Review;
import com.spring.boot.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
	
	//서비스 추가
	private final ReviewService reviewService;
	
	
	
	//리뷰작성권한 나중에 제한
	//리뷰작성창을 나중에 음,, mypage 에서 할거니까 
	//작성자의 로그인된 id 와 상품번호가 있어야 한다
	
	@PostMapping("/create/{id}")//여기에 productno도 줘야하는거 아닌 가 여..{productNo}
	public String createReview(Model model,@PathVariable("id") Integer id,
			@Valid ReviewForm reviewForm,BindingResult bindResult, Principal principal) {
		
		Review review = reviewService.getReview(pageable)
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

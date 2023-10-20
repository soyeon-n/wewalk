package com.spring.boot.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.dao.QuestionRepository;
import com.spring.boot.dto.AnswerForm;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.model.Answer;
import com.spring.boot.model.Product;
import com.spring.boot.model.Question;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.AnswerService;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.QuestionService;
import com.spring.boot.service.ReviewService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

//@RequestMapping("/answer")//prefix
@RequiredArgsConstructor
@Controller
public class AnswerController {
	private final AnswerService answerService;
	private final QuestionService questionService;
	private final UserService userService;
	private final ProductService productService;
	
	//답변작성
	@GetMapping("/answer/create/{id}")
	public String createAnswer(Model model,@PathVariable("id") Integer id,@Valid AnswerForm answerForm,BindingResult bindResult) {
		
		return "product_qna_answerForm";
	}
	
	@PostMapping("/answer/create/{id}")//질문한 글의 고유번호 question.id 를 가져옴
	public String createAnswer(Model model,@PathVariable("id") Integer id,
			@Valid AnswerForm answerForm ,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			BindingResult bindResult ) {
		
		Question question = questionService.getOneQuestion(id);//답변달고싶은 질문글을 가져옴 
		
		SiteUser siteUser = userService.getUserByUserName(principalDetails.getUsername());//답변작성자 정보를 가져옴
		
		if(bindResult.hasErrors()) {
			model.addAttribute("question",question);
			return "product_qna_list";
		}
		//답변작성
		Answer answer = answerService.answerCreate(question, answerForm.getContent(), siteUser);
		
		//답변작성과 동시에 답변완료 로 바뀌게하는것 done
		//set 을 내가 가져온 id에 따른 question 에 바꿔야함 
		//questionService.ansDone();
		
		
		Product product =question.getProduct();
		Integer pno=product.getId();
		
		//다시 상품페이지detail 로 돌아오기
		return String.format("redirect:/product/detail/%s", pno);
		
	}

}

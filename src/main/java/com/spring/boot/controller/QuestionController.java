package com.spring.boot.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.dto.AnswerForm;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.dto.QuestionForm;
import com.spring.boot.model.Answer;
import com.spring.boot.model.Product;
import com.spring.boot.model.Question;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.AnswerService;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.QuestionService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	private final AnswerService answerServices;
	private final UserService userService;
	private final ProductService productService;
	
	
	//여기서따로맵핑할필욘없지만 ?? ,왜냐면 product controller 에서 layout 에 보내줘야하니까 ??? 
	//일단은 여기서 게시판들을 확인할것 
	@GetMapping("/list/{productNo}")
	public String list(Model model, @PageableDefault Pageable pageable,@PathVariable("productNo") Integer productNo) {
		
		//그럼 main 에서 상품연결할때 hidden 으로 product.id 를 넘겨야?? 
		//여기서 hidden 으로 productNo 를 넘겨야 
		Product product = productService.getProductDetailByNo(productNo);//integer로 받아서 product 타입으로 바꿔준다 
		
		Page<Question> paging1 = questionService.getQuestionList(pageable, product);//product type 을 받아서 findby 가 가능
		
		model.addAttribute("paging1",paging1);
		model.addAttribute("product",product); // ${product.id} 뿌리기위해서 넘겨준다 
		
		
		return "product_qna_list";
		
	}
	//하나의 문의글 자세히 보기 >> 근데 난 모달창으로 할거임 주소만 만들어놓기 
	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id , AnswerForm answerForm) {
		
		Question question = questionService.getOneQuestion(id);
		model.addAttribute("question",question);//데이터 넘기기
		
		//답변은 하나만 보낼거야 헐답변 보낼필요없이 그냥 반복문으러 list 꺼ㅓ내는거였음 
		
		
		
		return "product_qna_answerList";
	}
	
	//문의글 작성
	@GetMapping("/create/{productNo}")
	public String questionCreate(QuestionForm questionForm, 
			Model model , @PathVariable("productNo")Integer productNo ) {
		
		//상품문의시 문의하려는 product-pname 이 들어와있으면 좋을듯 ? set 하기 
		
		Product product = productService.getProductDetailByNo(productNo);
		
		model.addAttribute(product);
		return "product_qna_questionForm";
	}
	
	@PostMapping("/create/{productNo}")
	public String questionCreate(@Valid QuestionForm questionForm,
			@PathVariable("productNo") Integer productNo,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			BindingResult bindResult ) {
		
		if(bindResult.hasErrors()) {//bindResult에 잘못된값이들어있으면 
			return "product_qna_questionForm";//다시 작성하도록
		}
		
		SiteUser siteUser = userService.getUserByUserName(principalDetails.getUsername());
		
		Product product = productService.getProductDetailByNo(productNo);
		
		
		
		//(title, content, siteUser, product);
		questionService.createQuestion(questionForm.getTitle(), questionForm.getContent(), siteUser, product);
		
		//다시 상품페이지detail 로 돌아오기
		return String.format("redirect:/product/detail/%s", productNo);
		
		
	}
	
	
	
	

}

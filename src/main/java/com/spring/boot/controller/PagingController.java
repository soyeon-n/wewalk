package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.model.Product;
import com.spring.boot.model.Question;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.QuestionService;
import com.spring.boot.service.ReviewService;

import lombok.RequiredArgsConstructor;


//ajax만을 위한 컨트롤러를 분리함 
@RequiredArgsConstructor
@RestController
public class PagingController {
	private final QuestionService questionService;
	private final ProductService productService;
	
	@Autowired
    public PagingController(QuestionService questionService) {
        this.questionService = questionService;
		this.productService = null;
    }
	
	
	
	
	
	@GetMapping(value = "/product/{productNo}", produces = "application/json")
    public Page<Question> getEntities(@RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                       @PathVariable("productNo") long productNo) {
		
		Product product = productService.getProductDetailByNo(productNo);
        PageRequest pageable = PageRequest.of(page, size);//원하는 page 와 size 를 pageable 에 담아서 이거를 pageable로 준다 
        return questionService.getQuestionList(pageable, product);
    }

}

package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.QuestionRepository;
import com.spring.boot.model.Product;
import com.spring.boot.model.Question;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	//product 상품의 전체 질문글 조회
	public Page<Question> getQuestionList(Pageable pageable,Product product){
		
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("Date"));
				
		//먼가여기를 기존의 페이징을 쓰면 안될거같은데,,,,,,,
		//pageable 페이징 추가
		pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1,
				pageable.getPageSize(),Sort.by(sorts));
		
		
		
		return questionRepository.findByProduct(product, pageable);
		
	}
	
	
	//하나의 question 가져오기
	public Question getOneQuestion(long id) {
		
		Optional<Question> op = questionRepository.findById(id);
		
		if(op.isPresent()) {
			return op.get();
		}else {
			throw new com.spring.boot.config.DataNotFoundException("question데이터가 없어요-_-;;");
			//http://localhost:8080/question/detail/1 이 id=글num 을 없는번호로 주면 뜬다 
		}
		
	}
	public void createQuestion(String title, String content,SiteUser siteUser,Product product) {
		//하 그니까 product 를 안넣어도 알아서 productNo = integer 를 넣으먄 되긴 하는데 
		Question question = new Question();
		question.setTitle(title);
		question.setContent(content);
		question.setDate(LocalDateTime.now());
		question.setUser(siteUser);
		question.setProduct(product);
		
		questionRepository.save(question);
		
	}
	//답변작성시 question 답변작성완료로 바뀌게되는것 추가
	/*
	public void ansDone(String true, Question question) {
		Question question = 
		question.setd
	}*/
	
	
	
	
	public void deleteQuestion(Question question) {
		questionRepository.delete(question);
	}
	
	

}

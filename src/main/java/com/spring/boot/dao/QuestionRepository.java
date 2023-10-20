package com.spring.boot.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Product;
import com.spring.boot.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	//특정상품의 모든 질문 출력
	Page<Question> findByProduct(Product product,Pageable pageable);//
	
	

}

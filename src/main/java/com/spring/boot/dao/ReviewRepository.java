package com.spring.boot.dao;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	//리뷰전체조회
	Page<Review> findAll(Pageable pageable);
	
	//추천수리뷰조회..는 all 에서 orderby 하면 되는거 아닌가
	
	
	
	

}

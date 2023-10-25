package com.spring.boot.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;

public interface ProductRepository extends JpaRepository<Product, Long>{
	//주의: 여기 레포에서 하나라도 안돌아가면 에러남 
	
	//전체상품 찾는 메소드
	Page<Product> findAll(Pageable pageable);//
	//pageable 을 돌려줘야함 대신 
	
	
	//productno로 상품 detail 찾는 메소드 
	Optional<Product> findById(long productNo);
	//Question findBySubjectAndContent(String subject, String content);//두개의조건으로셀렉하기
	
	
	
	//검색-카테고리검색
	List<Product> findByCategoryLike(String category);
	
	List<Product> findByStockGreaterThan(int stock);
    List<Product> findByStockEquals(int stock);
	
	//productlist-상품id 로 관련된 판매자siteuser의 다른상품추천하기 
    List<Product> findByUserId(long siteuser);
	


}

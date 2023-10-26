package com.spring.boot.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.spring.boot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product>{
	//주의: 여기 레포에서 하나라도 안돌아가면 에러남 
	
	
	//전체상품 찾는 메소드
	Page<Product> findAll(Pageable pageable);//
	//pageable 을 돌려줘야함 대신 
	
	
	//판매자id 로 product상품List 찾는 메소드
	Page<Product> findByUserId(long userId,Pageable pageable);
	//매개변수id 랑 pageable 둘다 줘야하나????????
	//이거아직 안씀
	
	//productno로 상품 detail 찾는 메소드 
	Optional<Product> findById(Long long1);
	//Question findBySubjectAndContent(String subject, String content);//두개의조건으로셀렉하기
	
	//검색어-상품이름name으로 상품찾는 메소드 
	//List<Product> findByNameLike(String name);
	
	//검색어-판매자이름으로 상품찾기
	//List<Product> findByUserIdLike(long id);
	
	//검색-카테고리검색
	List<Product> findByCategoryLike(String category);
	
	//소연
	Page<Product> findByUserId(Long userId, Pageable pageable);
	Page<Product> findByUserIdAndStockGreaterThan(Long userId, int stock, Pageable pageable);
	Page<Product> findByUserIdAndStockEquals(Long userId, int stock, Pageable pageable);
	
    List<Product> findByStockGreaterThan(int stock);
    List<Product> findByStockEquals(int stock);
    
    //판매량 순으로 검색(8개)
    List<Product> findByIdIn(List<Long> productnoList);
    long countByUserId(Long userId);


}

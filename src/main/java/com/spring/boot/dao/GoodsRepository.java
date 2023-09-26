package com.spring.boot.dao;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Goods;

@Repository
@SpringBootApplication
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

	//static List<Goods> findByAddedByUserEmail(String strieng) {
		//return null;
	
	}
	
    //판매자 닉네임으로 등록한 상품 가져오기 쿼리 구현하기
	//List<Goods> findByAddedByUserId(Integer userId);
    

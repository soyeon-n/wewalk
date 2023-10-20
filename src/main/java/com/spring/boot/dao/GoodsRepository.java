package com.spring.boot.dao;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Goods;

@Repository
@SpringBootApplication
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

	List<Goods> findByStockGreaterThan(int stock);
    List<Goods> findByStockEquals(int stock);
	
	
	}
    

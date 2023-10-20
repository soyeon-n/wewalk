package com.spring.boot.dao;


import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Goods;

@Repository
@SpringBootApplication
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

	Page<Goods> findByUserId(Long userId, Pageable pageable);
	
	Page<Goods> findByUserIdAndStockGreaterThan(Long userId, int stock, Pageable pageable);
	Page<Goods> findByUserIdAndStockEquals(Long userId, int stock, Pageable pageable);
	
    List<Goods> findByStockGreaterThan(int stock);
    List<Goods> findByStockEquals(int stock);
	
	}
    

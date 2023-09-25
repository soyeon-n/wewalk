package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
	
    List<Goods> findByAddedByUserId(Integer userId);
    
}
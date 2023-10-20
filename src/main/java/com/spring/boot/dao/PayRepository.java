package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.model.Pay;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long>{
	
	Pay findByUserId(Long userId);
    
}

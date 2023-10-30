package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.model.Pay;
import com.spring.boot.model.Point;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long>{
	
	Pay findByUserId(Long userId);
	
	List<Pay> findPaysByUserId(Long userId);
	
	Page<Pay> findByUserId(Long userId, Pageable pageable);
    
}

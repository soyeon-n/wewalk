package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.model.Point;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

	List<Point> findPointsByUserId(Long userId);
	
	Page<Point> findByUserId(Long userId, Pageable pageable);
	
}

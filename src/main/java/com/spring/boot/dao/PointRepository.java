package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Point;

public interface PointRepository extends JpaRepository<Point, Long>{

}

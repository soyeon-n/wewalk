package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.PointRepository;
import com.spring.boot.model.Point;

@Service
public class PointService {

	private final PointRepository pointRepository;

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public List<Point> findPointsByUserId(Long userId) {
        return pointRepository.findPointsByUserId(userId);
    }

	public Page<Point> getPointPaged(Long userId, int pageNum, int itemsPerPage) {
		
		Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
        // 사용자 ID를 사용하여 해당 사용자가 등록한 상품만 가져오도록 변경
        return pointRepository.findByUserId(userId, pageable);
        
	}
	
	public long getTotalItemCount() {
        return pointRepository.count();
    }
	
	
}

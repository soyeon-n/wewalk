package com.spring.boot.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.spring.boot.dao.PointRepository;
import com.spring.boot.model.Point;
import com.spring.boot.model.SiteUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointService {

	private final PointRepository pointRepository;
	
	public void saveUsePointHistory(SiteUser user, int point, String name) {
		
		Point newPoint = new Point();
		
		newPoint.setUser(user);
		newPoint.setPoint(-point);
		newPoint.setPayDate(new Date());
		newPoint.setType(name);
		
		pointRepository.save(newPoint);
	}
	
	public void saveGetPointHistory(SiteUser user, int point, String name) {
		
		Point newPoint = new Point();
		
		newPoint.setUser(user);
		newPoint.setPoint(point);
		newPoint.setPayDate(new Date());
		newPoint.setType(name+" 구매적립");
		
		pointRepository.save(newPoint);
		
	
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

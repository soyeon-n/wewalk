package com.spring.boot.service;

import java.util.Date;

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
	
}

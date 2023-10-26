package com.spring.boot.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.dao.PayRepository;
import com.spring.boot.model.Pay;
import com.spring.boot.model.SiteUser;

@Service
public class PayService {

	@Autowired
    private PayRepository payRepository;
	
	@Transactional
	public Pay save(Pay pay) {
        try {
            // Pay 객체를 데이터베이스에 저장
            return payRepository.save(pay);
            
        } catch (Exception e) {
            // 저장 과정에서 오류 발생 시 예외 처리
            e.printStackTrace();
            return null; // 또는 예외를 던지거나 다른 적절한 오류 처리를 수행할 수 있음
        }
    }
	
	public void savePayHistory(SiteUser user, int point, String name) {
		Pay pay = new Pay();
		pay.setUser(user);
		pay.setPayMoney(-point);
		pay.setPayDate(new Date());
		pay.setType(name + " 구매");
		payRepository.save(pay);
	}
	

}


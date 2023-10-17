package com.spring.boot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.PayRepository;
import com.spring.boot.dto.Pay;

@Service
public class PayService {

	@Autowired
    private PayRepository payRepository;
	
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


}
package com.spring.boot.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.spring.boot.dao.PayRepository;
import com.spring.boot.model.Pay;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayService {

	private final PayRepository payRepository;
	
	public void savePointHistory(SiteUser user, int point, String usePay) {
		Pay pay = new Pay();
		pay.setUser(user);
		pay.setPayAmount(point);
		pay.setPayDate(new Date());
		pay.setType(usePay);
		payRepository.save(pay);
	}
	
}

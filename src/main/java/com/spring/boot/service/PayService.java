package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.PayRepository;
import com.spring.boot.dto.Pay;
import com.spring.boot.dto.User;
@Service
public class PayService {
    
	private final PayRepository payRepository;
	private final UserService userService;
    
    @Autowired
    public PayService(PayRepository payRepository, UserService userService) {
        this.payRepository = payRepository;
        this.userService = userService;      
    }

    // 결제 정보를 저장하는 메서드
    public void processPayment(int userId, int payMoney) {
    	
        // 사용자 ID를 기반으로 사용자 정보를 가져오는 로직
    	User user = userService.getUserById(userId);

    	// 사용자의 pay 레코드를 찾아서 업데이트
        Pay pay = payRepository.findByUserId(userId);
        
        if (pay != null) {
        	
            // 이미 pay 레코드가 있는 경우 paymoney를 업데이트
            int currentPayMoney = pay.getPayMoney() + payMoney;
            pay.setPayMoney(currentPayMoney);
            
        } else {
            // pay 레코드가 없는 경우 새로 생성
            pay = new Pay();
            pay.setUser(user);
            pay.setPayMoney(payMoney);
        }

        // 결제 정보를 데이터베이스에 저장
        payRepository.save(pay);
    }
}

package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.ShippingRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.Shipping;
import com.spring.boot.dto.User;

@Service
public class ShippingService {

	@Autowired
    private ShippingRepository shippingRepository;

	@Autowired
    private UserRepository userRepository;

    public void registerShippingAddress(Long id) {
    	
    	/*
    	 
        User user = userRepository.findById(id).orElse(null);
        
        if (user!= null) {
            Shipping address = new Shipping();
            address.setUser(user);
            // 주소 등록에 필요한 다른 필드 설정
            shippingRepository.save(address); // 주소 정보를 저장
        }
        */
    }
    
}



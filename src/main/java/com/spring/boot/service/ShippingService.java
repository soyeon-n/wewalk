package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.ShippingRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.Shipping;
import com.spring.boot.model.SiteUser;


@Service
public class ShippingService {

    private final UserRepository userRepository;
    private final ShippingRepository shippingRepository;

    @Autowired
    public ShippingService(UserRepository userRepository, ShippingRepository shippingRepository) {
        this.userRepository = userRepository;
        this.shippingRepository = shippingRepository;
    }


    public void saveShipping(Shipping shipping) {
    	
    	 // Shipping 정보를 저장합니다.
        shippingRepository.save(shipping);
    }
    
    /* 창이 달라서 새로고침 불가능
    public boolean saveShipping(Shipping shipping) {
        try {
            // 배송지 정보를 저장합니다.
            shippingRepository.save(shipping);
            return true; // 성공했을 때 true 반환
        } catch (Exception e) {
            // 실패한 경우 false 반환
            return false;
        }
    }
    */

    @Transactional
    public List<Shipping> getShippingListByUserId(Long userId) {
    	
        // ShippingRepository를 사용하여 사용자 이메일에 해당하는 배송지 목록을 조회 (오름차순)
    	return shippingRepository.findByUserIdOrderByAnoAsc(userId);
    }
    
    public boolean deleteAddress(Integer ano) {
    	
        try {
            // 배송지 정보를 삭제합니다.
            shippingRepository.deleteById(ano);;
            return true;
            
        } catch (Exception e) {
            // 삭제에 실패한 경우 예외 처리
            return false;
        }
    }
   



    
}
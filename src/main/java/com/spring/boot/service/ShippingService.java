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

    /*
    public void registerShippingAddress(int userId, Shipping shipping) {
    	
    	// 주어진 userId로 사용자를 찾아옵니다.
        SiteUser user = UserRepository.findUserById(userId);

        if (user != null) {
            // Shipping 엔티티에 사용자 정보를 설정합니다.
            shipping.setUser(user);
            // Shipping 정보를 저장합니다.
            shippingRepository.save(shipping);
        } else {
            // 사용자를 찾지 못한 경우 예외 처리 또는 오류 처리를 수행할 수 있습니다.
            // 예: throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }
    */

    public void saveShipping(Shipping shipping) {
    	
    	 // Shipping 정보를 저장합니다.
        shippingRepository.save(shipping);
    }

    @Transactional
    public List<Shipping> getShippingListByUserId(Long userId) {
    	
        // ShippingRepository를 사용하여 사용자 이메일에 해당하는 배송지 목록을 조회
    	return shippingRepository.findByUserIdOrderByAnoDesc(userId);
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
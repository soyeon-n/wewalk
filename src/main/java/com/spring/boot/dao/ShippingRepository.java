package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Shipping;
import com.spring.boot.dto.User;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long>{

	// 사용자 ID를 기반으로 주소 등록
	/*User user = UserRepository.findById(id).orElse(null); 
	
	if (user != null) {
	    Shipping address = new Shipping();
	    address.setUser(user);
	    // 주소 등록에 필요한 다른 필드 설정
	    
	    ShippingRepository.save(address); // 주소 정보를 저장
	    
		}
	}	
	*/
}
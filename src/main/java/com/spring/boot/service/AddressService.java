package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.AddressRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.Address;
import com.spring.boot.model.SiteUser;


@Service
public class AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }


    public void saveAddress(Address address) {
    	
    	 // Shipping 정보를 저장합니다.
    	addressRepository.save(address);
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
    public List<Address> getAddressListByUserId(Long userId) {
    	
        // ShippingRepository를 사용하여 사용자 이메일에 해당하는 배송지 목록을 조회 (오름차순)
    	return addressRepository.findByUserIdOrderByIdAsc(userId);
    }
    
    public boolean deleteAddress(Long id) {
    	
        try {
            // 배송지 정보를 삭제합니다.
        	addressRepository.deleteById(id);;
            return true;
            
        } catch (Exception e) {
            // 삭제에 실패한 경우 예외 처리
            return false;
        }
    }
   



    
}
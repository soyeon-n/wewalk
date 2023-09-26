package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.ShippingRepository;
import com.spring.boot.dto.Shipping;

@Service
public class ShippingService {

	private final ShippingRepository shippingRepository;

	@Autowired
    public ShippingService(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    public Shipping saveShipping(Shipping shipping) {
        return shippingRepository.save(shipping);
    }
	
}

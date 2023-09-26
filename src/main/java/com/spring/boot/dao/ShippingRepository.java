package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long>{

	
}

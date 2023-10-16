package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.SellerRequest;

public interface SellerRequestRepository extends JpaRepository<SellerRequest, Long>{

}

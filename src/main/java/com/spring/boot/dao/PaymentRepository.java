package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Order;

public interface PaymentRepository extends JpaRepository<Order, Integer>{

}

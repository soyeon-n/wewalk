package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.OrderList;

public interface PaymentRepository extends JpaRepository<OrderList, Integer>{

}

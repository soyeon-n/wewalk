package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, String>{
	 List<OrderList> findByUserid(Long userid);
}

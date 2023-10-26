package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.spring.boot.model.OrderList;


public interface OrderListRepository extends JpaRepository<OrderList, String>, JpaSpecificationExecutor<OrderList>{
	 List<OrderList> findByUserid(Long userid);
	 List<OrderList> findOrderByUserId(Long userId);

}

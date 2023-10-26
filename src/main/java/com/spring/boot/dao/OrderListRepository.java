package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.spring.boot.model.OrderList;
import com.spring.boot.model.SiteUser;


public interface OrderListRepository extends JpaRepository<OrderList, Long>, JpaSpecificationExecutor<OrderList>{
	
	 List<OrderList> findByUser(SiteUser user);
	 List<OrderList> findOrderByUser(SiteUser user);

}

package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.spring.boot.model.OrderList;
import com.spring.boot.model.SiteUser;


public interface OrderListRepository extends JpaRepository<OrderList, Long>, JpaSpecificationExecutor<OrderList>{
	
	 List<OrderList> findByUser(SiteUser user);
	 List<OrderList> findOrderByUser(SiteUser user);
	 
	 @Query("SELECT o.productno, SUM(o.count) FROM OrderList o GROUP BY o.productno ORDER BY SUM(o.count) DESC")
	 Page<Object[]> findTopSellingProducts(Pageable pageable);
	 
}

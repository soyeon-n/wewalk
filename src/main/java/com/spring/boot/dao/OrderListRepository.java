package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.model.OrderList;
import com.spring.boot.model.Point;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long>{


	List<OrderList> findOrderByUserId(Long userId);

}

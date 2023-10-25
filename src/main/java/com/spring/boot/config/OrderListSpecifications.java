package com.spring.boot.config;

import org.springframework.data.jpa.domain.Specification;

import com.spring.boot.model.OrderList;

public class OrderListSpecifications {

	public static Specification<OrderList> topSoldProducts() {
        
		//productno로 groupby한 다음 count를 내림차순으로 정렬
		return (root, query, cb) -> {
            query.groupBy(root.get("productno")); //주문 내역을 productno 속성을 기준으로 그룹화
            query.orderBy(cb.desc(cb.sum(root.get("count")))); //각 productno별로 판매량(count 필드의 합)을 내림차순으로 정렬
            
            return cb.conjunction(); //현재의 Specification에서는 항상 true를 반환하는 조건을 나타내며, 이는 특별한 조건 없이 그룹화 및 정렬만 진행
        };
    }
	
}

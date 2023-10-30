package com.spring.boot.service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Selection;

import org.springframework.data.jpa.domain.Specification;

import com.spring.boot.model.OrderList;

public class OrderListSpecifications {

//	public static Specification<OrderList> topSellingProducts() {
//        return (root, query, criteriaBuilder) -> {
//            query.groupBy(root.get("productno"));
//            query.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(root.get("count"))));
//            Join<OrderList, Product> productJoin = root.join("productno", JoinType.INNER);
//            query.select(productJoin).distinct(true);
//            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
//        };
//    }
	
}

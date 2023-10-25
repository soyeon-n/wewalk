package com.spring.boot.config;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.spring.boot.model.Product;

public class ProductSpecification {

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }

            Predicate pnamePredicate = cb.like(root.get("pname"), "%" + keyword + "%");
            Predicate categoryPredicate = cb.like(root.get("category"), "%" + keyword + "%");
            
            //상품명 또는 상품 카테고리로 검색
            return cb.or(pnamePredicate, categoryPredicate); // pname 또는 category로 검색
                     
        };
    }
    
    public static Specification<Product> isGreaterThanZero() {
        return (root, query, cb) -> cb.greaterThan(root.get("id"), 0L);
    }
}
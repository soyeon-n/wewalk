package com.spring.boot.service;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.spring.boot.model.SiteUser;

public class SiteUserSpecification {

	//isActivated가 true인 user 정보만 가져오도록(또는 그 반대도) 하는 기능 추가 예정
    public static Specification<SiteUser> hasKeyword(String keyword, String type) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }

            Predicate userNamePredicate = cb.like(root.get("userName"), "%" + keyword + "%");
            Predicate emailPredicate = cb.like(root.get("email"), "%" + keyword + "%");
            
            if ("u".equals(type)) {
                return userNamePredicate;
            } else if ("e".equals(type)) {
                return emailPredicate;
            } else if ("ue".equals(type)){
                return cb.or(userNamePredicate, emailPredicate); // userName 또는 email로 검색
            }
			return null;                       
        };
    }
    
    public static Specification<SiteUser> isGreaterThanZero() {
        return (root, query, cb) -> cb.greaterThan(root.get("id"), 0L);
    }
}
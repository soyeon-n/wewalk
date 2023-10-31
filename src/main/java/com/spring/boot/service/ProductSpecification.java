package com.spring.boot.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.spring.boot.model.Product;

public class ProductSpecification {

	private static final Map<String, Boolean> CATEGORIES = new HashMap<>();

    static {
        // 카테고리를 HashMap에 추가
        String[] categories = {
            "데스크탑", "노트북", "PC주변기기", "모바일", "카메라", "영상",
            "주방", "계절", "미용", "건강", "여성의류", "남성의류",
            "아동의류", "잡화", "쥬얼리", "화장품", "바디", "가구", 
            "인테리어", "생활용품", "이벤트", "문구", "악기", "반려동물용품", 
            "스포츠의류", "자전거", "낚시", "도서", "아동도서", "학습", "음반",
            "쿠폰", "기타", "신선식품", "가공식품", "건강식품"
        };
        
        // 카테고리명이 일치할 때 true값 반환하도록 함
        for (String category : categories) {
            CATEGORIES.put(category, true);
        }
    }
	
    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) -> {
        	
        	
        	String lowerCaseKeyword = keyword.toLowerCase().trim();
        	
        	//db의 데이터를 소문자로 변환하여 대소문자 상관없이 검색 가능
        	Predicate pnameLikePredicate = cb.like(cb.lower(root.get("pname")), "%" + lowerCaseKeyword + "%");
        	Predicate categoryLikePredicate = cb.like(cb.lower(root.get("category")), "%" + lowerCaseKeyword + "%");
   	
        	if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }
        	
        	
        	//키워드가 카테고리로 검색하는 경우 정확한 카테고리 아이템만 검색            	
        	if (isCategorySearch(lowerCaseKeyword)) {
        		
        		String changedKeyword = "";
        		
        		//키워드 value값과 일치하지 않는 데이터 예외 처리
        		if(lowerCaseKeyword.equals("c_신발")) {
            		changedKeyword = "c_잡화";
            	}else if(lowerCaseKeyword.equals("c_침구")) {
            		changedKeyword = "c_인테리어";
            	}else if(lowerCaseKeyword.equals("c_생활")) {
            		changedKeyword = "c_생활용품";
            	}else if(lowerCaseKeyword.equals("c_여성 의류")) {
            		changedKeyword = "c_여성의류";
            	}else if(lowerCaseKeyword.equals("c_남성 의류")) {
            		changedKeyword = "c_남성의류";
            	}else if(lowerCaseKeyword.equals("c_아동 의류")) {
            		changedKeyword = "c_아동의류";
            	}else if(lowerCaseKeyword.equals("c_이벤트 용품")) {
            		changedKeyword = "c_이벤트";
            	}else if(lowerCaseKeyword.equals("c_국내도서")) {
            		changedKeyword = "c_도서";
            	}else {
            		changedKeyword = lowerCaseKeyword;
            	}
        		
                String category = changedKeyword.substring(2); // "c_"를 제외하고 소문자로 변환
                Predicate categoryPredicate = cb.equal(cb.lower(root.get("category")), category);
                return categoryPredicate;
            }
         	
            //상품명 또는 상품 카테고리로 검색(like)
            return cb.or(pnameLikePredicate, categoryLikePredicate); 	
            
        };
    }
    
    public static Specification<Product> isGreaterThanZero() {
        return (root, query, cb) -> cb.greaterThan(root.get("id"), 0L);
    }
    
    //카테고리 검색인지 검증
    private static boolean isCategorySearch(String keyword) {
        if (keyword != null && keyword.length() > 2 && keyword.startsWith("c_")) {
            String category = keyword.substring(2); // "c_"를 제외한 나머지 문자열
            return CATEGORIES.containsKey(category);
        }
        return false;
    }
}
package com.spring.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.Shipping;



@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Integer>{

	// 사용자 이메일에 해당하는 배송지 목록 조회 (오름차순)
	List<Shipping> findByUserIdOrderByAnoAsc(Long userId);
	//List<Shipping> findByUserIdOrderByAnoDesc(Long userId);
    
    //Shipping findByAno(int ano);

    // 배송지 번호로 배송지 삭제 (배송지 한개삭제하는거라서 void)
    //void deleteByAno(int ano);
    
}
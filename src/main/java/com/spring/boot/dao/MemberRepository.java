package com.spring.boot.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.dto.MyPage;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MyPage, Long> {
    
	Optional<MyPage> findById(String id);
	
}
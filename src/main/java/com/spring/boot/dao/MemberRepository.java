package com.spring.boot.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.dto.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
	Optional<Member> findById(Long id);
	
}
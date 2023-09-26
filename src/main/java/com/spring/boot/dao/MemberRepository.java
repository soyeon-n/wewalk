package com.spring.boot.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.dto.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, Long> {
    
	Optional<User> findById(String id);
	
}
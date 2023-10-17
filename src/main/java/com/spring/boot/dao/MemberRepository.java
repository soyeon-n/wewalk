package com.spring.boot.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.SiteUser;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<SiteUser, Long> {
    
	Optional<SiteUser> findById(String id);
	
}
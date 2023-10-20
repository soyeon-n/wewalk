package com.spring.boot.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.model.SiteUser;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<SiteUser, Long> {
    
	Optional<SiteUser> findById(String id);
	
	
	
}
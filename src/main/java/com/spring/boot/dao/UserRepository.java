package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long>{

	//사용자 정의
	//입력한 userName이 있는지 확인하기 위한 메소드
	Optional<SiteUser> findByUserName(String userName);
	Optional<SiteUser> findByEmail(String email);
	Optional<SiteUser> findByProviderId(String providerId);
	
}
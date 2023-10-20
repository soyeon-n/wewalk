package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
=======
import org.springframework.stereotype.Repository;
>>>>>>> SY

import com.spring.boot.model.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long>{

	//사용자 정의
	//입력한 userName이 있는지 확인하기 위한 메소드
	Optional<SiteUser> findByUserName(String userName);
	Optional<SiteUser> findByEmail(String email);
	Optional<SiteUser> findByProviderId(String providerId);
	
	// id를 기반으로 사용자를 찾는 메서드 추가
	//SiteUser findById(Long id);
	
}
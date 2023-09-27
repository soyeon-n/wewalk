package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.User;
import com.spring.boot.model.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long>{

	//사용자 정의
	//입력한 userName이 있는지 확인하기 위한 메소드
	Optional<SiteUser> findByUserName(String userName);
	
	// id를 기반으로 사용자를 찾는 메서드 추가
	User findById(int id);

	static User findUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
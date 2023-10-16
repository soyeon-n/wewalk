package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.SiteUser;

public interface AdminRepository extends JpaRepository<SiteUser, Long>{

	//페이지 만들어주는 메소드
	Page<SiteUser> findAll(Pageable pageable);
	
}

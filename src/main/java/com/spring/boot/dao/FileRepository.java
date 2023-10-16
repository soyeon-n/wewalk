package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.UserFiles;

public interface FileRepository extends JpaRepository<UserFiles, Long>{

	
	
}

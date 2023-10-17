package com.spring.boot.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.boot.model.SellerRequest;

public interface SellerRequestRepository extends JpaRepository<SellerRequest, Long>{

	Page<SellerRequest> findAll(Pageable pageable);
	
	//SellerRequest 불러오면서 동시에 파일들도 불러와야 할 때 사용
	@Query("SELECT sr FROM SellerRequest sr JOIN FETCH sr.userFilesList WHERE sr.id = :id")
	Optional<SellerRequest> findByIdAndFetchFilesEagerly(@Param("id") Long id);
	
}

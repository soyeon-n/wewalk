package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer> {

}

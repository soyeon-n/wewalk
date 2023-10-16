package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}

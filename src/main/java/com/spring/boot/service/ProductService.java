package com.spring.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.boot.dao.ProductRepository;
import com.spring.boot.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	
	public Product gerProductById(Integer id) {
		Product product = productRepository.findById(id).get();
		return product;
	}
	
	
}

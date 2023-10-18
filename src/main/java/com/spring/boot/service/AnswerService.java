package com.spring.boot.service;

import org.springframework.stereotype.Service;

import com.spring.boot.dao.AnswerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	

}

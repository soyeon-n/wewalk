package com.spring.boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{

}

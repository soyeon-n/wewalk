package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.dao.AnswerRepository;
import com.spring.boot.model.Answer;
import com.spring.boot.model.Question;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	//답변등록
	public Answer answerCreate(Question question, String content, SiteUser siteUser) {
		Answer answer = new Answer();
		
		answer.setContent(content);
		answer.setDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setSiteUser(siteUser);
		
		answerRepository.save(answer);
		
		return answer;
	}
	
	//하나의답변조회 >> 아마 상대방의 문의글 id 를 가져와야 그문의글에대한 답변글이 나오지 않나 ..? 
	
	
	
	
	
	

}

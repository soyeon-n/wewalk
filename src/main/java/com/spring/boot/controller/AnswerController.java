package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.boot.service.AnswerService;
import com.spring.boot.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")//prefix
@RequiredArgsConstructor
@Controller
public class AnswerController {
	private final AnswerService answerService;

}

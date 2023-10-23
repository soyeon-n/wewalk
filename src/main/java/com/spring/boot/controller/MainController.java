package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequestMapping("/wewalk")
@RequiredArgsConstructor
@Controller
public class MainController {

	@GetMapping("/main")
	public String mainPage() {
		
		return "mainPage";
	}
	
    @GetMapping("/search")
    @ResponseBody
    public String Search(@RequestParam("keyword") String keyword, Model model) {
        
    	
    	
        return keyword;
    }
	

}

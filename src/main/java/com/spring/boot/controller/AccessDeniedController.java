package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

	@GetMapping("/no-access")
    public String noAccess() {
        return "noAccess";
    }
	
}

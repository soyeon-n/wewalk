package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WewalkPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WewalkPayApplication.class, args);
	}

}

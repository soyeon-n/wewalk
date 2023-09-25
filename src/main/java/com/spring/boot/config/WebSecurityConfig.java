package com.spring.boot.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.spring.boot.model.BaseAuthRole;
//import com.spring.boot.service.BaseCustomOAuth2UserService;
//
//import lombok.RequiredArgsConstructor;
//

//해당 config는 SecurityConfig와 병합했으며 필요시 되살릴 예정
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//	@Autowired
//	private final BaseCustomOAuth2UserService baseCustomOAuth2UserService; 
//	
//	@Bean
//	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//		
//		http
//			.csrf().disable().headers().frameOptions().disable()
//			.and()
//			.authorizeRequests()
//			.antMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
//			.antMatchers("/api/vi/**").hasRole(BaseAuthRole.USER.name()) //USER권한 설정을 통해 모든 페이지에 접근 가능
//			.anyRequest().authenticated()
//			.and()
//			.logout().logoutSuccessUrl("/")
//			.and()
//			.oauth2Login().defaultSuccessUrl("/").userInfoEndpoint() //소셜 로그인 성공시 진입할 페이지 설정
//			.userService(baseCustomOAuth2UserService);
//		
//		return http.build();
//		
//	}
//	
//}

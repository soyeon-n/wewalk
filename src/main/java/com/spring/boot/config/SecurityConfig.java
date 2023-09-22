package com.spring.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.boot.service.UserSecurityService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	//스프링 Security에 UserSecurityService를 등록
	private final UserSecurityService userSecurityService;
	
	//WebSecurityConfig와 비교하여 Config 수정 필요
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	
		
		// 권한에 따라 허용하는 url 설정
        // /login, /signup 페이지는 모두 허용, 다른 페이지는 인증된 사용자만 허용
        http
            .authorizeRequests()
                .antMatchers("/**").permitAll();

		// login 설정
        http
            .formLogin()
                .loginPage("/user/login")    // GET 요청 (login form을 보여줌)
                .usernameParameter("email")	// login에 필요한 id 값을 email로 설정 (default는 username)
                .passwordParameter("password")	// login에 필요한 password 값을 password(default)로 설정
                .defaultSuccessUrl("/");	// login에 성공하면 /로 redirect

		// logout 설정
        http
        	.logout()
        		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) //logout주소가 들어오면 logout시킴
        		.logoutSuccessUrl("/").invalidateHttpSession(true); //logout되면 메인화면으로 돌려보내고 세션을 invalidate함

        return http.build();

	}
	
	//passwordEncoder 실행하면 BCrypt 암호화 객체를 반환
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		//AuthenticationManager는 스프링의 security 인증을 담당
		//AuthenticationManager Bean생성시 스프링의 내부 동작으로 위에서 작성한 UserSecurityService와 PasswordEncoder가 자동으로 설정됨
		
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
}
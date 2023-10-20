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

import com.spring.boot.model.UserRole;
import com.spring.boot.service.CustomOAuthSevice;
import com.spring.boot.service.PrincipalService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
	
	private final PrincipalService principalService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	//CustomOAuthService
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuthSevice customOAuthSevice) throws Exception {	
		
		//Routing Security
        http
        	.csrf().disable().headers().frameOptions().disable();// disable csrf for our requests 
        
        //.cors(); Cross-Origin Resource Sharing (CORS)를 활성화하는 메소드. 
        // CORS는 웹 페이지가 다른 도메인의 리소스에 액세스할 수 있게 하는 메커니즘
        http.cors();
        
        // 권한에 따라 허용하는 url 설정
        // /login, /signup 페이지는 모두 허용, 다른 페이지는 인증된 사용자만 허용
        http
	        .authorizeRequests()
	//        .antMatchers("/auth/oauthSignup", "/auth/signup", "/auth/login").access("not hasRole('USER') and not hasRole('SELLER')")
//		        .antMatchers("/auth/oauthSignup").hasRole(UserRole.OAUTH.name())
		        .antMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
		        .antMatchers("/user/**").hasRole(UserRole.USER.name())
		        .antMatchers("/seller/**").hasRole(UserRole.SELLER.name())
		        .anyRequest().permitAll()
	        .and()
	        .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint) //로그인 과정에서 비활성화된 계정에 대한 처리
            .accessDeniedHandler(customAccessDeniedHandler)
            .and()
            	.sessionManagement()
                	.invalidSessionUrl("/auth/login"); //로그인 후 접근 권한이 없는 경우에 대한 처리
        
//        , "/oauth2/authorization/**"
		// login 설정
        http
            .formLogin()
                .loginPage("/auth/login")    // GET 요청 (login form을 보여줌)
                .usernameParameter("userName")	// login에 필요한 id 값을 userName으로 설정 (default는 username)
                .passwordParameter("password")	// login에 필요한 password 값을 password(default)로 설정
                .defaultSuccessUrl("/")
                .failureHandler(customAuthenticationFailureHandler);
        
        //OAuth 2.0 login 설정
        http
            .oauth2Login()
            .defaultSuccessUrl("/auth/verify")
            .userInfoEndpoint() //소셜 로그인 성공시 진입할 페이지 설정
            .userService(customOAuthSevice);	// login에 성공하면 /로 redirect

		// logout 설정
        http
        	.logout()
        		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //logout주소가 들어오면 logout시킴
        		.logoutSuccessUrl("/").invalidateHttpSession(true); //logout되면 메인화면으로 돌려보내고 세션을 invalidate함

        return http.build();

	}
	
	//passwordEncoder 실행하면 BCrypt 암호화 객체를 반환
	//저사양 유저와 암호화의 안전성을 고려하여 해싱의 cost를 11로 설정
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(11);
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		//AuthenticationManager는 스프링의 security 인증을 담당
		//AuthenticationManager Bean생성시 스프링의 내부 동작으로 위에서 작성한 UserSecurityService와 PasswordEncoder가 자동으로 설정됨
		
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
}
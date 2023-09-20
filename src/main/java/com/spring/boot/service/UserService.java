package com.spring.boot.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exe.board.DataNotFoundException;
import com.spring.boot.user.SiteUser;
import com.spring.boot.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	//SecurityConfig에서 @Bean으로 메소드를 클래스화 시켰으므로 DI 됨
	private final PasswordEncoder passwordEncoder;
	
	//id 생성 메소드
	public SiteUser create(String userName, String email, String password) {
		
		SiteUser user = new SiteUser();
		
		user.setUserName(userName);
		user.setEmail(email);
		
		//패스워드 암호화(BCrypt해시 함수) -> SecurityConfig에 객체화된 메소드 생성
//		BCryptPasswordEncoder passwordEncoder = 
//				new BCryptPasswordEncoder();
		
		user.setPassword(passwordEncoder.encode(password));
		
		//회원정보 db에 저장
		userRepository.save(user);
		
		return user;
		
	}
	
	public SiteUser getUser(String userName) {
		
		Optional<SiteUser> siteUser = 
				userRepository.findByUserName(userName);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			throw new DataNotFoundException("User not found!");
		}
		
	}
	
}

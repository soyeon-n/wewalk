package com.spring.boot.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	//BCrypt해시 함수 호출
	private final PasswordEncoder passwordEncoder;
	
	//id 생성 메소드
	public SiteUser create(String userName, String email, String password) {
		
		SiteUser user = new SiteUser();
		
		user.setUserName(userName);
		user.setEmail(email);
		
		//암호화 처리
		user.setPassword(passwordEncoder.encode(password));
		
		//회원정보 db에 저장
		userRepository.save(user);
		
		return user;
		
	}
	
	//이메일로 불러오기
	public SiteUser getUserByEmail(String email) {
		
		Optional<SiteUser> siteUser = 
				userRepository.findByEmail(email);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			throw new DataNotFoundException("User not found!");
		}
		
	}
	
	//userName으로 불러오기
		public SiteUser getUserByUserName(String userName) {
			
			Optional<SiteUser> siteUser = 
					userRepository.findByUserName(userName);
			
			if(siteUser.isPresent()) {
				return siteUser.get();
			}else {
				throw new DataNotFoundException("User not found!");
			}
			
		}
}
package com.spring.boot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	//BCrypt해시 함수 호출
	private final PasswordEncoder passwordEncoder;
	
	//id 생성 메소드
	public SiteUser create(UserRole role, String email, String password, String userName,  
							String name, LocalDate birthDate, String address, 
							String detailAddress, String tel) {
		
		SiteUser user = new SiteUser();
		
		user.setRole(role);
		user.setEmail(email);
		//암호화 처리
		user.setPassword(passwordEncoder.encode(password));
		
		user.setUserName(userName);
		user.setName(name);
		user.setCreatedDate(LocalDateTime.now());
		user.setBirthDate(birthDate);
		user.setAddress(address);
		user.setDetailAddress(detailAddress);
		user.setTel(tel);
		
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
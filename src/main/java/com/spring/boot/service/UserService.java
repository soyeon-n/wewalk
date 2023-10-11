package com.spring.boot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.dao.BaseAuthUserRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	//private final BaseAuthUserRepository baseAuthUserRepository;
	
	private final UserRepository userRepository;
	
	//BCrypt해시 함수 호출
	private final PasswordEncoder passwordEncoder;
	
	//id 생성 메소드
	//id 생성 메소드
	public SiteUser create(UserRole role, String email, String password, String userName,  
							String name, LocalDate birthDate, String postcode, String address, 
							String detailAddress, String tel, String picture) {
		
		SiteUser user = SiteUser.builder()
								.role(role)
								.email(email)
								.password(passwordEncoder.encode(password))
								.userName(userName)
								.name(name)
								.createdDate(LocalDateTime.now())
								.birthDate(birthDate)
								.postcode(postcode)
								.address(address)
								.detailAddress(detailAddress)
								.tel(tel)
								.picture(picture)
								.build();
		
		//회원정보 db에 저장
		userRepository.save(user);
		
		return user;
	}
	
	public SiteUser getUser(Long id) {
		
		Optional<SiteUser> op = userRepository.findById(id);
		
		if(op.isPresent()) {
			
			return op.get();
			
		}else {
			
			throw new DataNotFoundException("데이터가 없습니다");
			
		}
		
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
	public SiteUser getUserByUsername(String userName) {
		
		Optional<SiteUser> siteUser = 
				userRepository.findByUserName(userName);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}else {
			throw new DataNotFoundException("User not found!");
		}
		
	}
	
	//사용자 정보 수정
	public void modify(SiteUser siteUser, String email, String userName, 
			String postcode, String address, String detailAddress, String tel) {
		
		siteUser.setEmail(email);
		siteUser.setUserName(userName);
		siteUser.setPostcode(postcode);
		siteUser.setAddress(address);
		siteUser.setDetailAddress(detailAddress);
		siteUser.setTel(tel);
		siteUser.setModifyDate(LocalDateTime.now());
		
		userRepository.save(siteUser);
		
	}
}
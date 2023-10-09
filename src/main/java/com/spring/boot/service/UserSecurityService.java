package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{

	//DB
	private final UserRepository userRepository;

	
	//사용자 명으로 비밀번호를 조회하여 return하는 메소드
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		//사용자명으로 SiteUser 객체를 조회
		Optional<SiteUser> searchUser = userRepository.findByEmail(email);
		
		//사용자명에 해당하는 데이터가 없을 경우
		if(!searchUser.isPresent()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
		}
		
		SiteUser siteUser = searchUser.get();
		
		List<GrantedAuthority> authorities = 
				new ArrayList<GrantedAuthority>();
		
		//사용자명이 "admin"인 경우 ADMIN 권한을 부여하고 그 외에는 일반 사용자 권한 부여
		//여기에서 authority 설정을 해줘야 SecurityConfig에서 authority를 검증해서 해당 페이지로 넘어갈 수 있음
		if("admin@wewalkpay.com".equals(email) || UserRole.ADMIN.getKey().equals(siteUser.getRoleKey())) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getKey()));
		}else {
			authorities.add(
					new SimpleGrantedAuthority(UserRole.USER.getKey()));
		}
		
		//사용자명(이메일), 비밀번호, 권한을 입력으로 스프링 Security의 User 객체를 생성하여 return
		return new User(siteUser.getEmail(), 
				siteUser.getPassword(), authorities);

	}	

}
package com.spring.boot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.dao.AdminRepository;
import com.spring.boot.dao.BaseAuthUserRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	//페이징
	public Page<SiteUser> getLists(Pageable pageable) {
		
		//날짜 기준으로 역순처리(listNum)
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("createdDate"));
		
		//실제로 페이지를 구현하는 클래스
		//한 페이지당 10개의 데이터가 보이도록 세팅
		pageable = PageRequest.of(
				pageable.getPageNumber() <= 0 ? 0 : 
					pageable.getPageNumber() -1 , 10, Sort.by(sorts));

//		//실제로 페이지를 구현하는 클래스
//		pageable = PageRequest.of(
//				pageable.getPageNumber() <= 0 ? 0 : 
//					pageable.getPageNumber() -1 , 
//					pageable.getPageSize() + 5, Sort.by(sorts));
		
		//getPageNumber() : 반환할 페이지(0보다 작으면 0으로 초기화)
		//PageRequest : 정렬 매개변수가 적용된 새 항목을 생성해줌
		//getPageSize() : 반환할 항목 수(디폴트 = 0)
		
		return adminRepository.findAll(pageable);
		
	}
	
	//admin으로 id 생성 메소드(계정 생성하는 프론트 화면 만들기) -> 이거 그냥 userservice로 하면 안되나?
	public SiteUser create(UserRole role, String email, String password, String userName,  
							String name, LocalDate birthDate, String postcode, String address, 
							String detailAddress, String tel, boolean seller) {
		
		SiteUser user = new SiteUser();
		
		user.setRole(role);
		user.setEmail(email);
		
		//암호화 처리(이미 Bcrypt를 통해 salt 적용됨)
		user.setPassword(passwordEncoder.encode(password));
		
		user.setUserName(userName);
		user.setName(name);
		user.setCreatedDate(LocalDateTime.now());
		user.setBirthDate(birthDate);
		user.setPostcode(postcode);
		user.setAddress(address);
		user.setDetailAddress(detailAddress);
		user.setTel(tel);
		user.setSeller(seller);
		
		//회원정보 db에 저장
		adminRepository.save(user);
		
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
	
	//
	public void modify(SiteUser siteUser, String email, String password, String userName, String name, 
			String postcode, String address, String detailAddress, String tel, 
			boolean seller, String birthYear, String birthMonth, String birthDay) {
		
		int year = Integer.parseInt(birthYear);
	    int month = Integer.parseInt(birthMonth);
	    int day = Integer.parseInt(birthDay);

	    LocalDate birthDate = LocalDate.of(year, month, day);
		
		siteUser.setEmail(email);
		siteUser.setPassword(passwordEncoder.encode(password));
		siteUser.setUserName(userName);
		siteUser.setName(name);
		siteUser.setPostcode(postcode);
		siteUser.setAddress(address);
		siteUser.setDetailAddress(detailAddress);
		siteUser.setTel(tel);
		siteUser.setSeller(seller);
		siteUser.setBirthDate(birthDate);
		siteUser.setModifyDate(LocalDateTime.now());
		
		adminRepository.save(siteUser);
		
	}
	
	//Question 자체를 삭제
	//엔티티 설정에 의해 question에 딸려있는 answer들도 전부 삭제됨
	public void delete(SiteUser siteUser) {
		adminRepository.delete(siteUser);
	}
	
}
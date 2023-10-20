package com.spring.boot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.config.SiteUserSpecification;
import com.spring.boot.dao.AdminRepository;
import com.spring.boot.dao.SellerRequestRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.PageRequestDTO;
import com.spring.boot.dto.PageResultDTO;
import com.spring.boot.dto.SiteUserDTO;
import com.spring.boot.model.SellerRequest;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final SellerRequestRepository sellerRequestRepository;
	
	//유저 리스트 페이징
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
		
		System.out.println("유저 리스트 페이징 처리");
		
		return adminRepository.findAll(pageable);
		
	}
	
	//검색기능을 포함한 Userlist
	public PageResultDTO<SiteUserDTO, SiteUser> getList(PageRequestDTO requestDTO) {
	    Pageable pageable = requestDTO.getPageable(Sort.by("id").ascending());
	    
	    Specification<SiteUser> spec = SiteUserSpecification.isGreaterThanZero();
	    
	    if (requestDTO.getType() != null) {
	        if (requestDTO.getType().equals("u") || requestDTO.getType().equals("e") || requestDTO.getType().equals("ue")) {
	            spec = spec.and(SiteUserSpecification.hasKeyword(requestDTO.getKeyword(), requestDTO.getType()));
	        }
	    }

	    Page<SiteUser> result = adminRepository.findAll(spec, pageable);
	    
	    Function<SiteUser, SiteUserDTO> fn = (entity -> entityToDto(entity));
	    
	    return new PageResultDTO<>(result, fn);
	}

	//판매자 요청 리스트 페이징
	public Page<SellerRequest> getSellerRequestLists(Pageable pageable) {
		
		//날짜 기준으로 역순처리(listNum)
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("requestTime"));
		
		//실제로 페이지를 구현하는 클래스
		//한 페이지당 10개의 데이터가 보이도록 세팅
		pageable = PageRequest.of(
				pageable.getPageNumber() <= 0 ? 0 : 
					pageable.getPageNumber() -1 , 10, Sort.by(sorts));

//			//실제로 페이지를 구현하는 클래스
//			pageable = PageRequest.of(
//					pageable.getPageNumber() <= 0 ? 0 : 
//						pageable.getPageNumber() -1 , 
//						pageable.getPageSize() + 5, Sort.by(sorts));
		
		//getPageNumber() : 반환할 페이지(0보다 작으면 0으로 초기화)
		//PageRequest : 정렬 매개변수가 적용된 새 항목을 생성해줌
		//getPageSize() : 반환할 항목 수(디폴트 = 0)
		
		System.out.println("판매자 요청 리스트 페이징 처리");
		
		return sellerRequestRepository.findAll(pageable);
		
	}
	
	//판매자 요청 처리(요청 취소도 있을 수 있음)
	public void approveRequest(SiteUser siteUser, SellerRequest request) {		

		siteUser.setSeller(!siteUser.isSeller());
		
		//세팅된 seller의 값이 true면 role을 seller로 set
		//판매자 신청한 경우
		if(siteUser.isSeller() == true) {			
			siteUser.setRole(UserRole.SELLER);
			siteUser.setIntro(request.getIntro());
		}else { //false면 role을 user로 set
			//판매자 취소 신청한 경우
			siteUser.setRole(UserRole.USER);
			siteUser.setIntro("");
		}
		
		//변경된 일시 저장
//			siteUser.setModifyDate(LocalDateTime.now());

		//해당 유저의 데이터 수정
		adminRepository.save(siteUser);

		//요청 승인(판매자 신청 or 판매자 취소 신청)
		if(request.isProcessed() == false) {	
			
			//처리여부 true
			request.setProcessed(true);
			request.setProcessedTime(LocalDateTime.now());

			//판매자 요청 데이터 수정
			sellerRequestRepository.save(request);
			
			System.out.println(siteUser.getUserName() + "의 요청 처리됨");
			
		}else { //승인처리 취소
			
			request.setProcessed(false);
			request.setProcessedTime(null);

			//판매자 요청 데이터 수정
			sellerRequestRepository.save(request);
			
			System.out.println(siteUser.getUserName() + "의 요청 처리 취소됨");
			
		}						
		
	}
	
	//판매자 요청 반려
	public void denyRequest(SellerRequest request) {		

		//처리여부 true
		request.setProcessed(true);
		request.setProcessedTime(LocalDateTime.now());
		request.setDenyReason("판매자 등록을 위한 자료 미비");
		
		System.out.println(request.getRequestUser().getUserName() + "의 요청 반려됨");
		
		//판매자 요청 데이터 수정
		sellerRequestRepository.save(request);
	}
	
	//admin으로 계정 생성 메소드
	public SiteUser create(UserRole role, String email, String password, String userName, 
    		String name, LocalDate birthDate, String postcode, 
    		String address, String detailAddress, String tel, boolean seller,
    		String picture, Integer point) {
		
		SiteUser user = SiteUser.builder()
				.role(role)
				.email(email)
				.password(passwordEncoder.encode(password)) // 암호화 처리
				.userName(userName)
				.name(name)
				.createdDate(LocalDateTime.now())
				.birthDate(birthDate)
				.postcode(postcode)
				.address(address)
				.detailAddress(detailAddress)
				.tel(tel)
				.seller(seller)
				.picture(picture)
				.point(point)
				.build();
		
		//회원정보 db에 저장
		adminRepository.save(user);
		
		System.out.println(LocalDateTime.now() + ":" + userName + "님 계정 정보 저장 중... ");
		
		return user;
		
	}
	
	//이메일로 불러오기
	public SiteUser getUserByEmail(String email) {
		
		Optional<SiteUser> siteUser = 
				userRepository.findByEmail(email);
		
		if(siteUser.isPresent()) {
			System.out.println(siteUser.get());
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
			System.out.println(siteUser.get());
			return siteUser.get();
		}else {
			throw new DataNotFoundException("User not found!");
		}
	}
	
	//수정
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
		
		System.out.println(siteUser + "로 수정");
		adminRepository.save(siteUser);
	}
	
	//논리적 삭제
	//비활성화 또는 활성화 모두 가능하게 함
	public void deactivateOrReactivate(SiteUser siteUser) {

		//물리적 삭제는 기한을 두고 진행할 수 있게 추후 업데이트 예정
		//		adminRepository.delete(siteUser);
		
		//현재 세팅되어있는 isActivated값 가져오기
		boolean isActivated = siteUser.isActivated();
		
		if(isActivated == true) {			
			System.out.println(siteUser + "비활성화");
		}else {
			System.out.println(siteUser + "활성화");			
		}
		
		siteUser.setActivated(!isActivated);
		
		adminRepository.save(siteUser);
	}
	
	public SiteUser dtoToEntity(SiteUserDTO dto){
		SiteUser entity = SiteUser.builder()
                .id(dto.getId())
                .role(dto.getRole())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .name(dto.getName())
                .picture(dto.getPicture())
                .provider(dto.getProvider())
                .isActivated(dto.isActivated())
                .build();
        return entity;
    }
	
	public SiteUserDTO entityToDto(SiteUser entity){

		SiteUserDTO dto = SiteUserDTO.builder()
                .id(entity.getId())
                .role(entity.getRole())
                .email(entity.getEmail())
                .userName(entity.getUserName())
                .name(entity.getName())
                .picture(entity.getPicture())
                .provider(entity.getProvider())
                .isActivated(entity.isActivated())
                .build();

        return dto;
    }
}
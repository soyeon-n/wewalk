package com.spring.boot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.boot.config.DataNotFoundException;
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
	
	//검색기능을 포함한 list
	public PageResultDTO<SiteUserDTO, SiteUser> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리

        Page<SiteUser> result = adminRepository.findAll(booleanBuilder,pageable); // Querydsl 사용

        Function<SiteUser,SiteUserDTO> fn = (entity -> entityToDto(entity));
        
        return new PageResultDTO<>(result,fn);
	}

	//
	 private BooleanBuilder getSearch(PageRequestDTO requestDTO){ // Querydsl 처리

		//검색유형(userName, email, u+e)
	    String type = requestDTO.getType();
	
	    BooleanBuilder booleanBuilder = new BooleanBuilder();
	
        QSiteUser qSiteUser = QSiteUser.siteUser;
	
        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qSiteUser.id.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0 ){ // 검색 조건이 없는경우
            return booleanBuilder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("u")){
            conditionBuilder.or(qSiteUser.userName.contains(keyword));
        }
        if(type.contains("e")){
            conditionBuilder.or(qSiteUser.email.contains(keyword));
        }
//        if(type.contains("ue")){
//            conditionBuilder.or(qSiteUser.writer.contains(keyword));
//        }

        // 모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
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
    		String picture, Long point) {
		
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
	
	//Question 자체를 삭제
	//엔티티 설정에 의해 question에 딸려있는 answer들도 전부 삭제됨
	public void delete(SiteUser siteUser) {
		System.out.println(siteUser + "삭제");
		adminRepository.delete(siteUser);
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
                .build();

        return dto;
    }
}
package com.spring.boot.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;

@Service
public class MembershipService {
	
	@Autowired
    UserRepository userRepository;

	public SiteUser save(SiteUser user) {
	    return userRepository.save(user);
	}
	
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 매일 한 번 실행 (24시간마다)
    public void checkAndExpireMemberships() {
		
        LocalDate currentDate = LocalDate.now();
        
        List <SiteUser> users = userRepository.findAll();
        
        for (SiteUser user : users) {
            if (user.isMembership() && currentDate.isAfter(user.getMembershipEndDate())) {
                user.setMembership(false); // 멤버십 만료
                userRepository.save(user);
            }
        }
		
	} 
	
}	
	

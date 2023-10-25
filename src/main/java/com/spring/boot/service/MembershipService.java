package com.spring.boot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}

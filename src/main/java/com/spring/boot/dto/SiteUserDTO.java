package com.spring.boot.dto;

import com.spring.boot.model.UserRole;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//세션으로 활용하려고 했으나 세션이 없다고 뜸
@Data
@NoArgsConstructor
public class SiteUserDTO{

	private Long id;
	private UserRole role;
    private String email;
    private String userName;
    private String name;
    private String picture;
    private String provider;
	private boolean isActivated;
    
    //사용자 유형 식별(ADMIN / SELLER / USER)
  	public String getRoleKey() {
  		return this.role.getKey();
  	}
    
  	@Builder
  	public SiteUserDTO(Long id, UserRole role, String email, String userName, 
  			String name, String picture, String provider, boolean isActivated) {
  		
  		this.id = id;
  		this.role = role;
  		this.email = email;
  		this.userName = userName;
  		this.name = name;
  		this.picture = picture;
  		this.provider = provider;
  		this.isActivated = isActivated;
  		
  	}
  	
}

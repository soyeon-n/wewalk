package com.spring.boot.service;

import org.springframework.transaction.annotation.Transactional;

public class MemberService {
	
	/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new Member(username, memberEntity.getPassword(), memberEntity.getEmail(), authorities);
    }
	
	@Transactional
    @Override
    public Long updateInfo(String username, String newName, String email) {
        Member member  = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        
        member.setUsername(newName);
        member.setEmail(email);
        return member.getId();
    }
    */

}

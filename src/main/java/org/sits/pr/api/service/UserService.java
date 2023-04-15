package org.sits.pr.api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sits.pr.api.entity.UserInfo;
import org.sits.pr.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
		
	public UserInfo saveUserInfo(UserInfo userInfo) {
		log.info ("Inside saveUserInfo method of User Service");
		String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(encodedPassword);
		return userRepository.save(userInfo);
	}
	
	public UserInfo getUserInfoByUserName(String userName)
	{
		return userRepository.findByUserName(userName);
	}


	public List<String> getRoles(Authentication authentication) {
		List<String> userRoles = new ArrayList<String>();

		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
		for (GrantedAuthority role : roles) {
			
			String strRole = role.getAuthority();
			log.debug("Role: "+strRole);
			userRoles.add(strRole);
		}
		
		return userRoles;
	}

}

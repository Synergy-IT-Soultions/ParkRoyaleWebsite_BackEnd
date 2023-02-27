package org.sits.pr.api.controller;

import org.sits.pr.api.entity.UserInfo;
import org.sits.pr.api.service.TokenService;
import org.sits.pr.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private TokenService tokenService;

	@Value("${org.sits.pr.api.adminAccess}")
	private String adminAccess;

	@PostMapping("/authenticate")
	@Operation(summary = "Authenticate an user and generate a token"
	, description = "Authenticate an user and generate a JWT Token ( that expires in few hours as "
			+ "mentioned in application.yml) and provide it to the client")
	public UserInfo auth(Authentication authentication) {
		UserInfo userInfo = userService.getUserInfoByUserName(authentication.getName());
		userInfo.setPassword("");
		userInfo.setToken(tokenService.generateToken(authentication));	
		return userInfo;
	}

	@PostMapping("/save")
	@Operation(summary = "Create / Update user information"
	, description = "Create an ADMIN user or update an ADMIN user so that they can edit the content of the page."
			+ " Only SYS_ADMIN can perform this operation")
	public UserInfo saveUser(@RequestBody UserInfo userInfo, Authentication authentication) throws Exception {
		if(!userService.getRoles(authentication).contains(adminAccess)) {
			throw new Exception("You do not have access to perform this action!");
		}
		
		return userService.saveUserInfo(userInfo);
	}
}

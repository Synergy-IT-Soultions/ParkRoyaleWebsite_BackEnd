package org.sits.pr.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C101_User_Id")
	private Long userId;
	
	@Column(name="C101_User_First_Name")
	private String firstName;
	
	@Column(name="C101_User_Last_Name")
	private String lastName;
	
	@Column(name="C101_User_Name")
	private String userName;
	
	@Column(name="C101_User_Password")
	private String password;
	
	@Column(name="C101_Is_Enabled")
	private int isEnabled;
	
	@Column(name="C101_Role")
	private String role;

}

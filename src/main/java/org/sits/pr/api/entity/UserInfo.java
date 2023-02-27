package org.sits.pr.api.entity;




import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="T101_User")
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="C101_User_Id")
	private long userId;
	
	@Column(name="C101_User_First_Name")
	private String firstName;
	
	@Column(name="C101_User_Last_Name")
	private String lastName;
	
	@Column(name="C101_User_Name")
	private String userName;
	
	@JsonIgnore
	@Column(name="C101_User_Password")
	private String password;
	
	@Column(name="C101_Is_Enabled")
	private int isEnabled;
	
	@Column(name="C101_Role")
	private String role;
	
	@Transient
	private String token;
	
}

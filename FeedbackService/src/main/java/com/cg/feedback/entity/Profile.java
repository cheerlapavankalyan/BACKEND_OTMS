package com.cg.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

	private String id;

	private String firstName;

	private String lastName;

	private String userName;

	private String password;

	private String gender;

	private String email;

	private String countryCode;

	private String phoneNumber;

	private String role;
}

package com.cg.profile.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "profile")
public class Profile {
	@Id
	private String id;

	@Pattern(regexp = "^[A-Z][a-zA-Z ]{1,}$", message = "First Name is required and should contain only alphabets and start with capital (^[A-Z][a-zA-Z ]{1,}$)")
	private String firstName;

	@Pattern(regexp = "^[A-Z][a-zA-Z]{1,}$", message = "Last Name is required and should contain only alphabets and sstart with capital (^[A-Z][a-zA-Z ]{1,}$)")
	private String lastName;

	@Pattern(regexp = "^[a-zA-Z0-9\\_@]{1,}$", message = "User Name is required and can have alphabets, numbers and special characters(^[A-Z][a-zA-Z ]{1,}$)")
	private String userName;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,19}$", message = "Password is required and should contain at least 1 capital & small letter, one num, one special char, & have a min 4 characters without white space")
	private String password;

	@Pattern(regexp = "^(Male|Female|Others)$", message = "Gender is required and should be only male, female and others (^(Male|Female|Others)$)")
	private String gender;

	@Pattern(regexp = "^[a-z0-9\\.-]+@[a-z]+\\.[a-z]{2,}$", message = "Email is required (^[a-z0-9]+@[a-z]+\\.[a-z]{2,}$)")
	private String email;
	
	@NotBlank(message = "Country code should not be blank")
	private String countryCode;

	@Pattern(regexp = "^[0-9]\\d{9}$", message = "Mobile number must be 10 digits")
	private String phoneNumber;

	@NotBlank(message = "Role should not be blank")
	private String role;
	
//	@Transient
//    private double percentage;
//    
//    public Profile(String username, double percentage) {
//        this.userName = username;
//        this.percentage = percentage;
//    }
}

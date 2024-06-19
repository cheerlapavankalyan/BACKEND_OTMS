package com.cg.profile.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sendEmail")
public class AdminVerification {
	@Id
	private String id;
	private String otp;
	private String email;
}

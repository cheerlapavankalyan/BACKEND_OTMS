package com.cg.profile.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@Pattern(regexp = "^[a-zA-Z0-9\\_@]{1,}$", message = "User Name is required and should can have alphabets, numbers and special characters(^[A-Z][a-zA-Z ]{1,}$)")
	private String username;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$", message = "Hint: Password should contain at least 1cap, 1sml, 1num, 1spcl char min 4 characters without white space")
	private String password;
}

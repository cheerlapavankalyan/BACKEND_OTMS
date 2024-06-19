package com.cg.profile.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.profile.entity.Profile;
import com.cg.profile.exception.ProfileException;
import com.cg.profile.exception.RecordAlreadyExistException;
import com.cg.profile.exception.RecordNotFoundException;
import com.cg.profile.request.LoginRequest;
import com.cg.profile.response.JSONResponse;
import com.cg.profile.service.CustomUserDetails;
import com.cg.profile.service.AdminAuthenticationServiceImpl;
import com.cg.profile.service.ProfileService;
import com.cg.profile.token.JwtUtility;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	AdminAuthenticationServiceImpl adminAuthenticationServiceImpl;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtility jwtUtility;

	@Autowired
	ProfileService profileService;

	@PostMapping("/signIn")
	public ResponseEntity<JSONResponse> signIn(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		if (authentication.isAuthenticated()) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			String jwtToken = jwtUtility.generateToken(loginRequest.getUsername());
			List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			JSONResponse jsonResponse = new JSONResponse(jwtToken, loginRequest.getUsername(), roles);
			return ResponseEntity.ok(jsonResponse);
		} else {
			throw new UsernameNotFoundException("Invalid credentials!");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Profile> registerAdmin(@Valid @RequestBody Profile profile)
			throws RecordAlreadyExistException, RecordNotFoundException, ProfileException {
		return ResponseEntity.ok(profileService.register(profile));
	}

	@PutMapping("/sendEmail/{otp}/{password}")
	public ResponseEntity<String> sendEmail(@PathVariable String otp, @PathVariable String password)
			throws RecordNotFoundException {
		profileService.resetPassword(otp, password);
		return ResponseEntity.ok("");
	}

	@PostMapping("/sendEmail/{OnPasswordReset}/{email}")
	public ResponseEntity<String> sendEmailPassword(@PathVariable String email) throws RecordNotFoundException {
		adminAuthenticationServiceImpl.StringSendEmailOnForgotPassword(email);
		return ResponseEntity.ok("");
	}
}
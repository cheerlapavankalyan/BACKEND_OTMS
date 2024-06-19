package com.cg.feedback.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.feedback.entity.Profile;


@FeignClient(name = "profile-service", contextId = "profileClient")
public interface ProfileClient {

	@GetMapping("/byUserName/{userName}")
	public ResponseEntity<Profile> getProfileByUserName(@PathVariable String userName);

	@GetMapping("/byEmail/{email}")
	public ResponseEntity<Profile> getProfileByEmail(@PathVariable String email);
}
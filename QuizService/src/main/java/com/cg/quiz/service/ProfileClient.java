package com.cg.quiz.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.quiz.entity.Profile;

@FeignClient(url = "http://localhost:7001", value = "Profile-Client")
public interface ProfileClient {

	@GetMapping("/byUserName/{userName}")
	public ResponseEntity<Profile> getProfileByUserName(@PathVariable String userName);
}
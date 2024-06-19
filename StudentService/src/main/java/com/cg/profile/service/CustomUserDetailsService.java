package com.cg.profile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.profile.entity.Profile;
import com.cg.profile.repository.ProfileRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	ProfileRepository profileRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Profile> userDetail = profileRepo.findByUserName(username);
		return userDetail.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

	}
}

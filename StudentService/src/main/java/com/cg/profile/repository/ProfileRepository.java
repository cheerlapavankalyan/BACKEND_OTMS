package com.cg.profile.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.profile.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String>{
	Optional<Profile> findByUserName(String userName);
	
	Optional<Profile> findByFirstNameIgnoreCase(String firstName);

	Optional<Profile> findByPhoneNumber(String phoneNumber);

	Optional<Profile> findByEmail(String email);
	
	void deleteByUserName(String userName);
	
}

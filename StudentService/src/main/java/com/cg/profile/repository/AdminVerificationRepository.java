package com.cg.profile.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cg.profile.entity.AdminVerification;

public interface AdminVerificationRepository extends MongoRepository<AdminVerification, String> {

	@Query(value = "Select o from AdminVerification o where o.email=?1")
	Optional<AdminVerification> findByEmail(String email);

//	Optional<AdminVerification> findFirstByEmail(String email);

	Optional<AdminVerification> findByOtp(String otp);
}

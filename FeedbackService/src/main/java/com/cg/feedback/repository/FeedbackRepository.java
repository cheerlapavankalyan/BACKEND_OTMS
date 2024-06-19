package com.cg.feedback.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.feedback.entity.Feedback;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String>{
	List<Feedback> findByQueId(String queId);
}

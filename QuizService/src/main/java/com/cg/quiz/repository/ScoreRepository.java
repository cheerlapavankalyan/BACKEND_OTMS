package com.cg.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.quiz.entity.Score;

@Repository
public interface ScoreRepository extends MongoRepository<Score, String>{
	List<Score> findByUserName(String userName);
	Optional<Score> findByUserNameAndQuizTitle(String userName, String quizTitle);
}

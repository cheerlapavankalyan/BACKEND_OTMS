package com.cg.quiz.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cg.quiz.entity.Quiz;

@Repository
public interface QuizRepo extends MongoRepository<Quiz, String> {
	Quiz findByTitle(String quizTitle );
}

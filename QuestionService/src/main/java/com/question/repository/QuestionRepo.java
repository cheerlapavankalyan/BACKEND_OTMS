package com.question.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.question.entities.Question;

public interface QuestionRepo extends MongoRepository<Question,String> {
    
	
//	@Query("{'quizTitle':?0}")
	 List<Question> findByQuizTitle(@Param ("quizTitle")String quizTitle );
}

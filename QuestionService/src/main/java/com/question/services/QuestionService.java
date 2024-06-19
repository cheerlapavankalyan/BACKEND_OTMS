package com.question.services;

import java.util.List;

import com.question.entities.Question;
import com.question.exception.QuestionNotFoundException;


public interface QuestionService {
	Question addQue(Question question);
	List<Question> getAllQues();
	Question getQue(String id)throws QuestionNotFoundException;
	Question updateQue(String id,Question question) throws QuestionNotFoundException;
	List<Question> findByQuizTitle(String quizTitle);
	String deleteQue(String queId)throws QuestionNotFoundException;
}

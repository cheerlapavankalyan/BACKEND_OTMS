package com.cg.quiz.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.quiz.entity.Question;

@FeignClient(url = "http://localhost:8082", value = "Question-Client")
public interface QuestionClient {
	@GetMapping("/question/quiz/{quizTitle}")
	public List<Question> getQuestionOfQuiz(@PathVariable String quizTitle);
}

package com.cg.feedback.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.feedback.entity.Question;

//@FeignClient(name = "question-service", contextId = "questionClient")
@FeignClient(url = "http://localhost:8082", value = "Question-Client")
public interface QuestionClient {
	
	@GetMapping("question/{id}")
	public ResponseEntity<Question> getOne(@PathVariable String id);
	
		
	
}

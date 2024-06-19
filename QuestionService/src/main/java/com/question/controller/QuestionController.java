package com.question.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.question.entities.Question;
import com.question.exception.QuestionNotFoundException;
import com.question.services.QuestionService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	 
	@GetMapping("/test")
	public String getTestMsg()
	{
		return "this is question microservice";
	}
	
	@PostMapping("/createque")
	public Question create(@RequestBody Question question)
	{
		return questionService.addQue(question);
	}
	
	@GetMapping()
	public List<Question> get()
	{
		return questionService.getAllQues();
	}
	
	@GetMapping("/{id}")
	public Question getOne(@PathVariable String id)throws QuestionNotFoundException
	{
		return questionService.getQue(id);
	}
	
	
	@GetMapping("/quiz/{quizTitle}")
	public List<Question> getQuestionOfQuiz(@PathVariable String quizTitle)
	{
		return questionService.findByQuizTitle(quizTitle);
	}
	
	@PutMapping("/updateque/{queId}")
	public Question updateQue(@PathVariable String queId,@RequestBody Question que)throws QuestionNotFoundException
	{
		return questionService.updateQue(queId, que);
	}
	
	@DeleteMapping("deleteque/{queId}")
	public String deleteQue(@PathVariable String queId)throws QuestionNotFoundException {
		return questionService.deleteQue(queId);
	}
	
}

package com.cg.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.quiz.entity.Quiz;
import com.cg.quiz.entity.Score;
import com.cg.quiz.exception.ScoreNotFoundException;
import com.cg.quiz.exception.TestNotFoundException;
import com.cg.quiz.service.QuizService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping("/create")
	public String create(@RequestBody Quiz quiz) {
		return quizService.addQuiz(quiz);
	}

	@GetMapping()
	public List<Quiz> get() {
		return quizService.getAllQuiz();
	}

	@GetMapping("/{id}")
	public Quiz getOne(@PathVariable String id) throws TestNotFoundException {
		return quizService.getQuiz(id);
	}

	@GetMapping("/quizdata/{quizTitle}")
	public List<Quiz> getQuizbyTitle(@PathVariable String quizTitle) {
		return quizService.getQuizByQuizTitle(quizTitle);
	}


	@PutMapping("/updateQuiz/{id}")
	public Quiz updateQuiz(@PathVariable String id, @RequestBody Quiz quiz) throws TestNotFoundException {
		return quizService.updateQuiz(id, quiz);
	}

	@DeleteMapping("/deleteQuiz/{id}")
	public String deleteQuiz(@PathVariable String id) throws TestNotFoundException {
		return quizService.deleteQuiz(id);
	}

	@PostMapping("/scoreBoard")
	public ResponseEntity<Score> saveScore(@RequestBody Score score) throws ScoreNotFoundException {
		Score s = quizService.saveScore(score);
		return ResponseEntity.ok(s);
	}
	
	@GetMapping("/getAllScores/{userName}")
	public List<Score> getAllScoresByUsername(@PathVariable String userName) {
		return quizService.getAllScoresByUsername(userName);
	}
}

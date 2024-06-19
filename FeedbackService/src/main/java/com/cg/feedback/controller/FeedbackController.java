package com.cg.feedback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.feedback.entity.Feedback;
import com.cg.feedback.exception.FeedbackException;
import com.cg.feedback.service.FeedbackService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@PostMapping("/giveFeedback")
	public ResponseEntity<Feedback> giveFeedback(@RequestBody Feedback feedback) throws FeedbackException {
		Feedback savedFeedback = feedbackService.giveFeedback(feedback);
		return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
	}

	@GetMapping("/getFeedbacksByQuestion/{queId}")
	public ResponseEntity<List<Feedback>> getAllFeedbacksByQuestion(@PathVariable String queId) {
		List<Feedback> fb = feedbackService.getAllFeedbacksByQuestion(queId);
		return new ResponseEntity<>(fb, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Feedback>> getAllFeedbacks() {
		List<Feedback> fb = feedbackService.getAllFeedbacks();
		return new ResponseEntity<>(fb, HttpStatus.OK);
	}

	@GetMapping("/updateSeenStatus")
	public ResponseEntity<List<Feedback>> updateSeenStatus() {
		List<Feedback> fb = feedbackService.updateSeenStatus();
		return new ResponseEntity<>(fb, HttpStatus.OK);
	}
}

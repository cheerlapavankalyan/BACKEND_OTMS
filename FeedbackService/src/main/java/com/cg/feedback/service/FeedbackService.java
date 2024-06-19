package com.cg.feedback.service;

import java.util.List;

import com.cg.feedback.entity.Feedback;
import com.cg.feedback.exception.FeedbackException;

public interface FeedbackService {

	Feedback giveFeedback(Feedback feedback)throws FeedbackException;

	List<Feedback> getAllFeedbacksByQuestion(String question);

	List<Feedback> getAllFeedbacks();

	List<Feedback> updateSeenStatus();
}

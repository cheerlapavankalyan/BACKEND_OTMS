package com.cg.feedback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.feedback.entity.Feedback;
import com.cg.feedback.entity.Profile;
import com.cg.feedback.entity.Question;
import com.cg.feedback.exception.FeedbackException;
import com.cg.feedback.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepo;

	@Autowired
	private ProfileClient profileClient;

	@Autowired
	private QuestionClient questionClient;

	@Override
	public Feedback giveFeedback(Feedback feedback) throws FeedbackException{
		ResponseEntity<Profile> p = profileClient.getProfileByUserName(feedback.getUserName());
		ResponseEntity<Question> que = questionClient.getOne(feedback.getQueId());
		if (p.getBody() != null || p.getBody().getRole().equalsIgnoreCase("USER")) {
			feedback.setUserName(p.getBody().getUserName());
			feedback.setQueId(que.getBody().getQueId());
			feedback.setStudentFeedback(feedback.getStudentFeedback());
		} else {
			throw new FeedbackException("Feedback Not Found");
		}
		return feedbackRepo.save(feedback);
	}

	@Override
	public List<Feedback> getAllFeedbacksByQuestion(String queId) {
		List<Feedback> fb = feedbackRepo.findByQueId(queId);
		if (fb.size() != 0) {
			return fb;
		}
		return new ArrayList<>();
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		List<Feedback> feedbacks = feedbackRepo.findAll();
		for (Feedback fb : feedbacks) {
			fb.setUser(profileClient.getProfileByUserName(fb.getUserName()).getBody());
			fb.setQue(questionClient.getOne(fb.getQueId()).getBody());
		}
		return feedbacks;
	}

	@Override
	public List<Feedback> updateSeenStatus() {
		List<Feedback> feedbacks = feedbackRepo.findAll();
		for (Feedback fb : feedbacks) {
			if (!fb.isSeenStatus()) {
				fb.setSeenStatus(true);
				feedbackRepo.save(fb);
			}
			fb.setUser(profileClient.getProfileByUserName(fb.getUserName()).getBody());
			fb.setQue(questionClient.getOne(fb.getQueId()).getBody());
		}
		return feedbacks;
	}
}

package com.cg.quiz.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.quiz.entity.Profile;
import com.cg.quiz.entity.Quiz;
import com.cg.quiz.entity.Score;
import com.cg.quiz.exception.ScoreNotFoundException;
import com.cg.quiz.exception.TestNotFoundException;
import com.cg.quiz.repository.QuizRepo;
import com.cg.quiz.repository.ScoreRepository;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	private QuizRepo quizRepo;

	@Autowired
	private ScoreRepository scoreRepo;

	@Autowired
	private QuestionClient questionClient;

	@Autowired
	private ProfileClient profileClient;

	@Override
	public String addQuiz(Quiz quiz) {
		List<Quiz> quizzes = quizRepo.findAll();
		String reponseMsg = null;
		boolean isAdded = false;
		for (Quiz q : quizzes) {
			if (q.getTitle().toLowerCase().equalsIgnoreCase(quiz.getTitle().toLowerCase())) {
				isAdded = true;
				reponseMsg = "Test already exists";
				break;
			}

		}
		if (!isAdded) {
			quizRepo.save(quiz);
			reponseMsg = "Test added successfully";
		}

		return reponseMsg;

	}

	@Override
	public List<Quiz> getAllQuiz() {
		List<Quiz> quizzes = quizRepo.findAll();
		List<Quiz> newQuizList = new ArrayList<Quiz>();
		for (Quiz quiz : quizzes) {
			quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getTitle()));
			newQuizList.add(quiz);
		}
		return newQuizList;
	}

	@Override
	public Quiz getQuiz(String id) throws TestNotFoundException{
		Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new TestNotFoundException("Test not found"));
		quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getTitle()));
		return quiz;
	}

	@Override
	public Quiz updateQuiz(String id, Quiz quiz) throws TestNotFoundException {
		Quiz q = getQuiz(id);
		q.setTitle(quiz.getTitle());
		q.setQuizImg(quiz.getQuizImg());
		return quizRepo.save(q);
	}

	@Override
	public String deleteQuiz(String id) throws TestNotFoundException{
		Optional<Quiz> details= quizRepo.findById(id);
		if(details.isPresent()) {
			quizRepo.delete(details.get());
			return "Record deleted Successfully";
		}
		throw new TestNotFoundException("Test is not deleted: Test not found with id " + id);
//		quizRepo.deleteById(id);
	}

	@Override
	public List<Quiz> getQuizByQuizTitle(String quizTitle) {
		List<Quiz> quizzes = quizRepo.findAll();
		List<Quiz> newQuiz = new ArrayList<Quiz>();
		for (Quiz quiz : quizzes) {

			if (quiz.getTitle().equalsIgnoreCase(quizTitle)) {
				newQuiz.add(quiz);
			}
		}
		return newQuiz;
	}

	@Override
	public Score saveScore(Score score) throws ScoreNotFoundException{
		Optional<Score> existingScore = scoreRepo.findByUserNameAndQuizTitle(score.getUserName(), score.getQuizTitle());
		if (existingScore.isPresent()) {
			scoreRepo.delete(existingScore.get());
		}
		ResponseEntity<Profile> p = profileClient.getProfileByUserName(score.getUserName());
		if (p.getBody() != null || p.getBody().getRole().equalsIgnoreCase("USER")) {
			score.setUserName(p.getBody().getUserName());
			score.setQuizTitle(score.getQuizTitle());
			score.setPercentage(score.getPercentage());
			score.setDateTaken(LocalDate.now());
		} else {
			throw new ScoreNotFoundException("Not Found");
		}
		return scoreRepo.save(score);
	}

	@Override
	public List<Score> getAllScoresByUsername(String username) {
		return scoreRepo.findByUserName(username);
	}


}
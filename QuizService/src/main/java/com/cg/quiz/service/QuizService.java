package com.cg.quiz.service;

import java.util.List;

import com.cg.quiz.entity.Quiz;
import com.cg.quiz.entity.Score;
import com.cg.quiz.exception.ScoreNotFoundException;
import com.cg.quiz.exception.TestNotFoundException;

public interface QuizService {
	String addQuiz(Quiz quiz);

	List<Quiz> getAllQuiz();

	Quiz getQuiz(String id)throws TestNotFoundException;

	List<Quiz> getQuizByQuizTitle(String quizTitle);
	
//	Quiz getQuizByQuizTitle(String quizTitle);

	Quiz updateQuiz(String id, Quiz quiz) throws TestNotFoundException;

	String deleteQuiz(String id) throws TestNotFoundException;
	
	Score saveScore(Score score)throws ScoreNotFoundException;
	
    List<Score> getAllScoresByUsername(String username);
}

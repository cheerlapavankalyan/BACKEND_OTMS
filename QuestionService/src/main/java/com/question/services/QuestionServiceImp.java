package com.question.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.question.entities.Question;
import com.question.exception.QuestionNotFoundException;
import com.question.repository.QuestionRepo;

@Service
public class QuestionServiceImp implements QuestionService {

	@Autowired
	QuestionRepo questionRepo;

	@Override
	public Question addQue(Question question) {
		return questionRepo.save(question);
	}

	@Override
	public List<Question> getAllQues() {
		return questionRepo.findAll();
	}


	@Override
	public Question getQue(String id)throws QuestionNotFoundException {
		return questionRepo.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found"));
	}

	@Override
	public Question updateQue(String id, Question question) throws QuestionNotFoundException {
		Question que = getQue(id);
		que.setQue(question.getQue());
		que.setQuizTitle(question.getQuizTitle());
		que.setQuizTitle(question.getQuizTitle());
		que.setOption1(question.getOption1());
		que.setOption2(question.getOption2());
		que.setOption3(question.getOption3());
		que.setOption4(question.getOption4());
		que.setCorrectOpt(question.getCorrectOpt());
		return questionRepo.save(que);
	}

	@Override
	public String deleteQue(String queId)throws QuestionNotFoundException {
		Optional<Question> details= questionRepo.findById(queId);
		if(details.isPresent()) {
			questionRepo.delete(details.get());
			return "Record deleted Successfully";
		}
		throw new QuestionNotFoundException("Question is not deleted: Question not found with id " + queId);
//		 questionRepo.deleteById(queId);
	}

	@Override
	public List<Question> findByQuizTitle(String quizTitle) {
		return questionRepo.findByQuizTitle(quizTitle);
	}


}

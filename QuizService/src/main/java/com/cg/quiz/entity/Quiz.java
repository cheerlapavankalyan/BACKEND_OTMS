package com.cg.quiz.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "quiz")
public class Quiz {
	@Id
	String id;
	String title;
	String quizImg;

	// NOT SAVE IN DB
	transient private List<Question> questions;
}

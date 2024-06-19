package com.cg.quiz.entity;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "scores")
public class Score {
	@Id
	private String id;
	private String userName;
	private String quizTitle;
	private double percentage;
	private LocalDate dateTaken;
}

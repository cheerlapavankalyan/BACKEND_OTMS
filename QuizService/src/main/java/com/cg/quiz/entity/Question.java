package com.cg.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Question {
	String queId;
	String quizTitle;
	String que;
	String option1;
	String option2;
	String option3;
	String option4;
	String correctOpt;
}
package com.cg.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
package com.cg.profile.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "feedback")

public class Feedback {
	@Id
	private String id;
    private String userName;
    private String quizTitle;
    private String question;
    private String studentFeedback;
}

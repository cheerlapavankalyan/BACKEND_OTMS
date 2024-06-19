
package com.question.controller.test;
 
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import java.util.Arrays;

import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
 
import com.fasterxml.jackson.databind.ObjectMapper;

import com.question.controller.QuestionController;

import com.question.entities.Question;

import com.question.exception.QuestionNotFoundException;

import com.question.services.QuestionService;
 
@WebMvcTest(QuestionController.class)

public class QuestionControllerTest {
 
    @Autowired

    private MockMvc mockMvc;
 
    @MockBean

    private QuestionService questionService;
 
    @Autowired

    private ObjectMapper objectMapper;
 
    private Question question1;

    private Question question2;
 
    @BeforeEach

    public void setUp() {

        question1 = new Question("1", "Quiz1", "Question1", "A", "B", "C", "D", "A");

        question2 = new Question("2", "Quiz1", "Question2", "A", "B", "C", "D", "B");

    }
 
    @Test

    public void testGetTestMsg() throws Exception {

        mockMvc.perform(get("/question/test"))

                .andExpect(status().isOk())

                .andExpect(content().string("this is question microservice"));

    }
 
    @Test

    public void testCreateQuestion() throws Exception {

        when(questionService.addQue(any(Question.class))).thenReturn(question1);
 
        mockMvc.perform(post("/question/createque")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(question1)))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.queId").value("1"));
 
        verify(questionService, times(1)).addQue(any(Question.class));

    }
 
    @Test

    public void testGetAllQuestions() throws Exception {

        List<Question> questions = Arrays.asList(question1, question2);

        when(questionService.getAllQues()).thenReturn(questions);
 
        mockMvc.perform(get("/question"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].queId").value("1"))

                .andExpect(jsonPath("$[1].queId").value("2"));
 
        verify(questionService, times(1)).getAllQues();

    }
 
    @Test

    public void testGetOneQuestion() throws Exception {

        when(questionService.getQue("1")).thenReturn(question1);
 
        mockMvc.perform(get("/question/1"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.queId").value("1"));
 
        verify(questionService, times(1)).getQue("1");

    }
 
//    @Test

//    public void testGetOneQuestionNotFound() throws Exception {

//        when(questionService.getQue("1")).thenThrow(new QuestionNotFoundException("Question not found"));

//

//        mockMvc.perform(get("/question/1"))

//                .andExpect(status().isNotFound());

//

//        verify(questionService, times(1)).getQue("1");

//    }
 
    @Test

    public void testGetQuestionOfQuiz() throws Exception {

        List<Question> questions = Arrays.asList(question1, question2);

        when(questionService.findByQuizTitle("Quiz1")).thenReturn(questions);
 
        mockMvc.perform(get("/question/quiz/Quiz1"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].queId").value("1"))

                .andExpect(jsonPath("$[1].queId").value("2"));
 
        verify(questionService, times(1)).findByQuizTitle("Quiz1");

    }
 
    @Test

    public void testUpdateQuestion() throws Exception {

        when(questionService.updateQue(eq("1"), any(Question.class))).thenReturn(question1);
 
        mockMvc.perform(put("/question/updateque/1")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(question1)))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.queId").value("1"));
 
        verify(questionService, times(1)).updateQue(eq("1"), any(Question.class));

    }
 
//    @Test

//    public void testUpdateQuestionNotFound() throws Exception {

//        when(questionService.updateQue(eq("1"), any(Question.class))).thenThrow(new QuestionNotFoundException("Question not found"));

//

//        mockMvc.perform(put("/question/updateque/1")

//                .contentType(MediaType.APPLICATION_JSON)

//                .content(objectMapper.writeValueAsString(question1)))

//                .andExpect(status().isNotFound());

//

//        verify(questionService, times(1)).updateQue(eq("1"), any(Question.class));

//    }
 
//    @Test

//    public void testDeleteQuestion() throws Exception {

//        doNothing().when(questionService).deleteQue("1");

//

//        mockMvc.perform(delete("/question/deleteque/1"))

//                .andExpect(status().isOk())

//                .andExpect(content().string("Question with ID: 1 deleted successfully."));

//

//        verify(questionService, times(1)).deleteQue("1");

//    }
 
//    @Test

//    public void testDeleteQuestionNotFound() throws Exception {

//        doThrow(new QuestionNotFoundException("Question not found")).when(questionService).deleteQue("1");

//

//        mockMvc.perform(delete("/question/deleteque/1"))

//                .andExpect(status().isNotFound());

//

//        verify(questionService, times(1)).deleteQue("1");

//    }

}

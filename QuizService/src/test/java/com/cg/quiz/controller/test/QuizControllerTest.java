package com.cg.quiz.controller.test;
 
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import com.cg.quiz.controller.QuizController;
import com.cg.quiz.entity.Question;
import com.cg.quiz.entity.Quiz;
import com.cg.quiz.entity.Score;
import com.cg.quiz.exception.TestNotFoundException;
import com.cg.quiz.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@WebMvcTest(QuizController.class)
public class QuizControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private QuizService quizService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private Quiz quiz1;
    private Quiz quiz2;
    private Score score;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
 
        Question question1 = new Question("1", "Quiz1", "Question1", "A", "B", "C", "D", "A");
        Question question2 = new Question("2", "Quiz1", "Question2", "A", "B", "C", "D", "B");
        List<Question> questions = Arrays.asList(question1, question2);
 
        quiz1 = new Quiz("1", "Quiz1", "quiz1.jpg", questions);
        quiz2 = new Quiz("2", "Quiz2", "quiz2.jpg", questions);
        score = new Score("1", "john_doe", "Quiz1", 95.0, LocalDate.now());
    }
 
    @Test
    public void testCreateQuiz() throws Exception {
        when(quizService.addQuiz(any(Quiz.class))).thenReturn("Quiz created successfully");
 
        mockMvc.perform(post("/quiz/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quiz1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Quiz created successfully"));
 
        verify(quizService, times(1)).addQuiz(any(Quiz.class));
    }
 
    @Test
    public void testGetAllQuizzes() throws Exception {
        List<Quiz> quizzes = Arrays.asList(quiz1, quiz2);
        when(quizService.getAllQuiz()).thenReturn(quizzes);
 
        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].title").value("Quiz1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].title").value("Quiz2"));
 
        verify(quizService, times(1)).getAllQuiz();
    }
    @Test
    public void testGetOneQuiz() throws Exception {
        when(quizService.getQuiz("1")).thenReturn(quiz1);
 
        mockMvc.perform(get("/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Quiz1"));
 
        verify(quizService, times(1)).getQuiz("1");
    }
    @Test
    public void testGetQuizByTitle() throws Exception {
        List<Quiz> quizzes = Arrays.asList(quiz1, quiz2);
        when(quizService.getQuizByQuizTitle("Quiz1")).thenReturn(quizzes);
 
        mockMvc.perform(get("/quiz/quizdata/Quiz1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Quiz1"))
                .andExpect(jsonPath("$[1].title").value("Quiz2"));
 
        verify(quizService, times(1)).getQuizByQuizTitle("Quiz1");
    }
    @Test
    public void testUpdateQuiz() throws Exception {
        when(quizService.updateQuiz(eq("1"), any(Quiz.class))).thenReturn(quiz1);
 
        mockMvc.perform(put("/quiz/updateQuiz/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quiz1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Quiz1"));
 
        verify(quizService, times(1)).updateQuiz(eq("1"), any(Quiz.class));
    }
    @Test
    public void testDeleteQuiz() throws Exception {
        when(quizService.deleteQuiz("1")).thenReturn("Quiz deleted successfully");
 
        mockMvc.perform(delete("/quiz/deleteQuiz/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Quiz deleted successfully"));
 
        verify(quizService, times(1)).deleteQuiz("1");
    }
    @Test
    public void testSaveScore() throws Exception {
        when(quizService.saveScore(any(Score.class))).thenReturn(score);
 
        mockMvc.perform(post("/quiz/scoreBoard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(score)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("john_doe"))
                .andExpect(jsonPath("$.quizTitle").value("Quiz1"));
 
        verify(quizService, times(1)).saveScore(any(Score.class));
    }
    @Test
    public void testGetAllScoresByUsername() throws Exception {
        List<Score> scores = Arrays.asList(score);
        when(quizService.getAllScoresByUsername("john_doe")).thenReturn(scores);
 
        mockMvc.perform(get("/quiz/getAllScores/john_doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("john_doe"))
                .andExpect(jsonPath("$[0].quizTitle").value("Quiz1"));
 
        verify(quizService, times(1)).getAllScoresByUsername("john_doe");
    }
//    @Test
//    public void testGetOneQuiz_QuizNotFoundException() throws Exception {
//        when(quizService.getQuiz("999")).thenThrow(new TestNotFoundException("Quiz not found"));
//
//        mockMvc.perform(get("/quiz/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Quiz not found"));
//
//        verify(quizService, times(1)).getQuiz("999");
//    }
//    @Test
//    public void testSaveScore_InvalidScoreException() throws Exception {
//        Score invalidScore = new Score();
//        invalidScore.setUserName("john_doe");
//
//        mockMvc.perform(post("/quiz/scoreBoard")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(invalidScore)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Score details are incomplete"));
//
//        verify(quizService, never()).saveScore(any(Score.class));
//    }
    @Test
    public void testGetAllScoresByUsername_ScoresFound() throws Exception {
        List<Score> scores = Arrays.asList(score);
        when(quizService.getAllScoresByUsername("john_doe")).thenReturn(scores);
 
        mockMvc.perform(get("/quiz/getAllScores/john_doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("john_doe"))
                .andExpect(jsonPath("$[0].quizTitle").value("Quiz1"));
 
        verify(quizService, times(1)).getAllScoresByUsername("john_doe");
    }
 
 
    
}
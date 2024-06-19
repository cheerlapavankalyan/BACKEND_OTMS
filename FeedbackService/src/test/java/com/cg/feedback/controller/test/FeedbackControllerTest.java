package com.cg.feedback.controller.test;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Arrays;
import java.util.List;
 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 
import com.cg.feedback.controller.FeedbackController;
import com.cg.feedback.entity.Feedback;
import com.cg.feedback.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FeedbackController.class)
public class FeedbackControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private FeedbackService feedbackService;
 
    @InjectMocks
    private FeedbackController feedbackController;
 
    private ObjectMapper objectMapper;
 
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
        objectMapper = new ObjectMapper();
    }
 
    @Test
    public void testGiveFeedback() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setId("1");
        feedback.setUserName("JohnDoe");
        feedback.setQuizTitle("Sample Quiz");
        feedback.setQueId("q1");
        feedback.setQueNo("1");
        feedback.setStudentFeedback("Great question!");
        feedback.setFeedbackDate("2024-06-18");
        feedback.setSeenStatus(false);
 
        when(feedbackService.giveFeedback(any(Feedback.class))).thenReturn(feedback);
 
        mockMvc.perform(post("/feedback/giveFeedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userName").value("JohnDoe"))
                .andExpect(jsonPath("$.quizTitle").value("Sample Quiz"))
                .andExpect(jsonPath("$.queId").value("q1"))
                .andExpect(jsonPath("$.queNo").value("1"))
                .andExpect(jsonPath("$.studentFeedback").value("Great question!"))
                .andExpect(jsonPath("$.feedbackDate").value("2024-06-18"))
                .andExpect(jsonPath("$.seenStatus").value(false));
    }
    @Test
    public void testGetAllFeedbacks() throws Exception {
        Feedback feedback1 = new Feedback("1", "JohnDoe", "Sample Quiz", "q1", "1", "Great question!", "2024-06-18", false, null, null);
        Feedback feedback2 = new Feedback("2", "JaneDoe", "Sample Quiz 2", "q2", "2", "Needs improvement.", "2024-06-19", true, null, null);
 
        List<Feedback> feedbackList = Arrays.asList(feedback1, feedback2);
 
        when(feedbackService.getAllFeedbacks()).thenReturn(feedbackList);
 
        mockMvc.perform(get("/feedback")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(feedbackList.size()))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userName").value("JohnDoe"))
                .andExpect(jsonPath("$[0].quizTitle").value("Sample Quiz"))
                .andExpect(jsonPath("$[0].queId").value("q1"))
                .andExpect(jsonPath("$[0].queNo").value("1"))
                .andExpect(jsonPath("$[0].studentFeedback").value("Great question!"))
                .andExpect(jsonPath("$[0].feedbackDate").value("2024-06-18"))
                .andExpect(jsonPath("$[0].seenStatus").value(false))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].userName").value("JaneDoe"))
                .andExpect(jsonPath("$[1].quizTitle").value("Sample Quiz 2"))
                .andExpect(jsonPath("$[1].queId").value("q2"))
                .andExpect(jsonPath("$[1].queNo").value("2"))
                .andExpect(jsonPath("$[1].studentFeedback").value("Needs improvement."))
                .andExpect(jsonPath("$[1].feedbackDate").value("2024-06-19"))
                .andExpect(jsonPath("$[1].seenStatus").value(true));
    }
    @Test
    public void testGetAllFeedbacksByQuestion() throws Exception {
        Feedback feedback1 = new Feedback("1", "JohnDoe", "Sample Quiz", "q1", "1", "Great question!", "2024-06-18", false, null, null);
        Feedback feedback2 = new Feedback("2", "JaneDoe", "Sample Quiz", "q1", "2", "Needs improvement.", "2024-06-19", true, null, null);
 
        List<Feedback> feedbackList = Arrays.asList(feedback1, feedback2);
 
        when(feedbackService.getAllFeedbacksByQuestion("q1")).thenReturn(feedbackList);
 
        mockMvc.perform(get("/feedback/getFeedbacksByQuestion/q1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(feedbackList.size()))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userName").value("JohnDoe"))
                .andExpect(jsonPath("$[0].quizTitle").value("Sample Quiz"))
                .andExpect(jsonPath("$[0].queId").value("q1"))
                .andExpect(jsonPath("$[0].queNo").value("1"))
                .andExpect(jsonPath("$[0].studentFeedback").value("Great question!"))
                .andExpect(jsonPath("$[0].feedbackDate").value("2024-06-18"))
                .andExpect(jsonPath("$[0].seenStatus").value(false))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].userName").value("JaneDoe"))
                .andExpect(jsonPath("$[1].quizTitle").value("Sample Quiz"))
                .andExpect(jsonPath("$[1].queId").value("q1"))
                .andExpect(jsonPath("$[1].queNo").value("2"))
                .andExpect(jsonPath("$[1].studentFeedback").value("Needs improvement."))
                .andExpect(jsonPath("$[1].feedbackDate").value("2024-06-19"))
                .andExpect(jsonPath("$[1].seenStatus").value(true));
    }
    @Test
    public void testUpdateSeenStatus() throws Exception {
        Feedback feedback1 = new Feedback("1", "JohnDoe", "Sample Quiz", "q1", "1", "Great question!", "2024-06-18", true, null, null);
        Feedback feedback2 = new Feedback("2", "JaneDoe", "Sample Quiz", "q2", "2", "Needs improvement.", "2024-06-19", true, null, null);
 
        List<Feedback> feedbackList = Arrays.asList(feedback1, feedback2);
 
        when(feedbackService.updateSeenStatus()).thenReturn(feedbackList);
 
        mockMvc.perform(get("/feedback/updateSeenStatus")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(feedbackList.size()))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userName").value("JohnDoe"))
                .andExpect(jsonPath("$[0].quizTitle").value("Sample Quiz"))
                .andExpect(jsonPath("$[0].queId").value("q1"))
                .andExpect(jsonPath("$[0].queNo").value("1"))
                .andExpect(jsonPath("$[0].studentFeedback").value("Great question!"))
                .andExpect(jsonPath("$[0].feedbackDate").value("2024-06-18"))
                .andExpect(jsonPath("$[0].seenStatus").value(true))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].userName").value("JaneDoe"))
                .andExpect(jsonPath("$[1].quizTitle").value("Sample Quiz"))
                .andExpect(jsonPath("$[1].queId").value("q2"))
                .andExpect(jsonPath("$[1].queNo").value("2"))
                .andExpect(jsonPath("$[1].studentFeedback").value("Needs improvement."))
                .andExpect(jsonPath("$[1].feedbackDate").value("2024-06-19"))
                .andExpect(jsonPath("$[1].seenStatus").value(true));
    }
}
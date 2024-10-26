package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Feedback;
import com.project.KoiOrderingSystem.model.FeedbackRequest;
import com.project.KoiOrderingSystem.service.FeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class FeedbackAPI {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity createFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAll();
        return ResponseEntity.ok(feedbackList);
    }
}

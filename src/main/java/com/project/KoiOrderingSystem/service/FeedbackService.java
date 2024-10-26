package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.Feedback;
import com.project.KoiOrderingSystem.model.FeedbackRequest;
import com.project.KoiOrderingSystem.repository.FeedbackRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BookingService bookingService;

    public Feedback createFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback();
        Booking booking = bookingService.getBookingById(feedbackRequest.getBookingId());
        feedback.setCreateAt(new Date());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setComment(feedbackRequest.getComment());
        feedback.setCustomer(authenticationService.getCurrentAccount());
        feedback.setBooking(booking);

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }
}

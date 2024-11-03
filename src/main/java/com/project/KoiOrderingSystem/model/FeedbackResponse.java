package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class FeedbackResponse {
    long id;

    int rating;

    String comment;

    Date createAt;

}

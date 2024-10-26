package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}

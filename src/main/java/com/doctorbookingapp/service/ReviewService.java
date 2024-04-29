package com.doctorbookingapp.service;
import com.doctorbookingapp.entity.Review;
import java.util.List;

public interface ReviewService {

    Review createReview(Review review);

    List<Review> getAllReviews();


    List<Review> getReviewsByPatientId(Long patientId);


    List<Review> getReviewByDoctorId(long doctorId);
}


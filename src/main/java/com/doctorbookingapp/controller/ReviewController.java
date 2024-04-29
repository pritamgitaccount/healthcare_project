package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.entity.Review;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.DoctorDto;
import com.doctorbookingapp.repository.DoctorRepository;
import com.doctorbookingapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private DoctorRepository doctorRepository;

    //http://localhost:8080/api/reviews
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        if (review.getRating() >= 0 && review.getRating() <= 5) {
            Review createdReview = reviewService.createReview(review);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Review rating must be between 0 to 5.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getReviewsByDoctorId(@PathVariable long doctorId) {
        // Step 1: Retrieve the Doctor by ID from the repository
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new ResourceNotFoundException("Doctor not found with ID: " + doctorId)
        );
        // Step 2: Retrieve the list of Reviews for the Doctor from the service
        List<Review> reviews = reviewService.getReviewByDoctorId(doctorId);

        // Step 3: Calculate the overall rating percentage using the provided method
        double overallRatingPercentage = calculateOverallRatingPercentage(reviews);

        // Step 4: Create a DoctorDto object using the retrieved Doctor, Reviews, and overall rating percentage
        DoctorDto dto = new DoctorDto(doctor, reviews, overallRatingPercentage);

        // Step 5: Return the DoctorDto in a ResponseEntity with HTTP status OK
        return ResponseEntity.ok(dto);
    }


    private double calculateOverallRatingPercentage(List<Review> reviews) {
        // Step 1: Create a stream of Review objects from the provided list
        OptionalDouble averageRating = reviews.stream()

                // Step 2: Map each Review to its rating (extracting the rating)
                .mapToDouble(Review::getRating)

                // Step 3: Calculate the average rating using the DoubleStream.average() method
                .average();

        // Step 4: Retrieve the average rating if present, otherwise use a default value of 0.0
        // Step 5: Convert the average rating to a percentage (assuming ratings are on a scale of 0 to 5)
        return averageRating.orElse(0.0) / 5.0 * 100.0;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Review>> getReviewsByPatientId(@PathVariable Long patientId) {
        List<Review> reviews = reviewService.getReviewsByPatientId(patientId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}

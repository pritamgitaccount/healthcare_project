package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.entity.Review;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.repository.DoctorRepository;
import com.doctorbookingapp.repository.PatientRepository;
import com.doctorbookingapp.repository.ReviewRepository;
import com.doctorbookingapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public Review createReview(Review review) {
        // Fetch the Doctor entity by its ID
        Doctor doctor = doctorRepository.findById(review.getDoctor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + review.getDoctor().getId()));

        // Fetch the Patient entity by its ID
        Patient patient = patientRepository.findById(review.getPatient().getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + review.getPatient().getPatientId()));

        // Set the Doctor and Patient references in the Review
        review.setDoctor(doctor);
        review.setPatient(patient);

        // Save the Review object to the database
        return reviewRepository.save(review);
    }


    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewByDoctorId(long doctorId){
        return reviewRepository.findByDoctorId(doctorId);

    }

    @Override
    public List<Review> getReviewsByPatientId(Long patientId) {
        // Implement this method to retrieve reviews by patient ID
        // You can use reviewRepository.findByPatientId(patientId) if needed
        return null;
    }
}

package com.doctorbookingapp.payload;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.OptionalDouble;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private Doctor doctor;
    private List<Review> reviews;
    private double overallRatingPercentage;

    public double calculateOverallRatingPercentage() {
        OptionalDouble averageRating = reviews.stream()
                .mapToDouble(Review::getRating) //It's equivalent to writing a lambda expression like (review) -> review.getRating().
                .average();

        return averageRating.orElse(0.0) / 5.0 * 100.0;
        // Assuming ratings are on a scale of 0 to 5
    }
}

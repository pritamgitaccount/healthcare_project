package com.doctorbookingapp.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardDto {
    private Long id;

//    @NotNull(message = "Ward name is required")
    @Size(min = 2, max = 100, message = "Ward name should be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Total beds should be at least 0")
    @Max(value = 1000, message = "Total beds cannot exceed 1000")
    private int totalBeds;

    @Min(value = 0, message = "Available beds should be at least 0")
    private int availableBeds;

    private List<@Valid BedDto> beds;  // Validation will cascade to BedDto

    // Getters and Setters
}


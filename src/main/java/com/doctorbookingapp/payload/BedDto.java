package com.doctorbookingapp.payload;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BedDto {
    private Long id;

    @NotNull(message = "Bed status is required")
    private String status;  // e.g., "Occupied", "Available", "Maintenance"

    // Getters and Setters
}


package com.doctorbookingapp.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
    private Long id;

//    @NotNull(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name should be between 2 and 100 characters")
    private String name;

    // Getters and Setters
}

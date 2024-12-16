package com.doctorbookingapp.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto {
    private Long id;

    @NotNull(message = "Hospital name is required")
    @Size(min = 2, max = 100, message = "Hospital name should be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Location is required")
    @Size(min = 2, max = 150, message = "Location should be between 2 and 150 characters")
    private String location;

    @NotNull(message = "Contact information is required")
    private String contactInfo;

    // These fields are optional
    private List< DepartmentDto> departments;
    private List< WardDto> wards;

    public HospitalDto(String name) {
    }
}

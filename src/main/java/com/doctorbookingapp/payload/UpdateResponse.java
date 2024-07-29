package com.doctorbookingapp.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponse {
    private PatientDto patientDto;
    private String message;
}


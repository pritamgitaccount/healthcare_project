package com.doctorbookingapp.payload;
import lombok.Data;

@Data
public class PatientDto {
    private Long patientId;
    private String name;
    private String disease;
    private int age;
    private String mobile;
    private String email;

}

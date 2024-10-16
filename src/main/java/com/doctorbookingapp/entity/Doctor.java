package com.doctorbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
@JsonIgnoreProperties("reviews")
public class Doctor {

    // Add the following constructor for writing test cases
    public Doctor(String doctorName, String specialization, String hospital) {
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.hospital = hospital;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DOCTOR_NAME", nullable = false)
    private String doctorName;

    @Column(name = "QUALIFICATION", nullable = false)
    private String qualification;

    @Column(name = "SPECIALIZATION", nullable = false)
    private String specialization;

    @Column(name = "EXPERIENCE", nullable = false)
    private int experience;

    @Column(name = "DESCRIPTION")
    private String descriptions;

    @Column(name = "HOSPITAL")
    private String hospital;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


}
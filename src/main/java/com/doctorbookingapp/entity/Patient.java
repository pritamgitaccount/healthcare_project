package com.doctorbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
@JsonIgnoreProperties("reviews")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "disease", nullable = false)
    private String disease;
    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "mobile", nullable = false, unique = true)
    private String mobile;

    @Column(name = "email")
    private String email;

    // Add the OneToMany relationship with Review
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
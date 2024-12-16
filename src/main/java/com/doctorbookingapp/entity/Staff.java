package com.doctorbookingapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private String shift;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    // Getters and Setters
}


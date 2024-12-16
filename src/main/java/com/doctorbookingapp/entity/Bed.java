package com.doctorbookingapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;  // e.g., Occupied, Available, Maintenance

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    // Getters and Setters
}

package com.doctorbookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name; // e.g., ROLE_PATIENT, ROLE_DOCTOR, ROLE_ADMIN

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "role")
    private Set<UserRole>users = new HashSet<>();
}

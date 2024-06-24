package com.doctorbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 150)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, length = 150)
    private String username;

    @Column(name = "email", unique = true, length = 250)
    private String email;

    @JsonIgnore
    @Column(name = "user_role", nullable = false, length = 40)
    private String userRole;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
}
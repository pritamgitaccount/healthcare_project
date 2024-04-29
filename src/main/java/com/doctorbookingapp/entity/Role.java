package com.doctorbookingapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


// Role.java
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=60 , nullable = false)
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users = new HashSet<>();

}


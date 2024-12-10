package com.doctorbookingapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;
    private String contactInfo;
    private String location;


}
package com.doctorbookingapp.repository;


import com.doctorbookingapp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Here custom query for sql written down
   // Optional<Patient> findByUsername(String userName);
//
//    //Whenever we call this incomplete method like this , Spring Boot internally generates HQL query
//    // (Hibernate query language) for this, and it will start searching for the database
   Optional<Patient> findByEmail(String email);
//
//    Optional<Patient> findByUsernameOrEmail(String userName, String email);
//
//    Boolean existsByUsername(String username);
//
//    Boolean existsByEmail(String email);
}

package com.doctorbookingapp.repository;

import com.doctorbookingapp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d " +
            "WHERE lower(d.doctorName) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(d.specialization) LIKE lower(concat('%', :search, '%')) " +
            "OR lower(d.hospital) LIKE lower(concat('%', :search, '%'))")
    List<Doctor> searchByNameOrSpecializationOrHospital(@Param("search") String search);
}
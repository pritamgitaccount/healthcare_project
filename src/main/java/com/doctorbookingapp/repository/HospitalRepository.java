package com.doctorbookingapp.repository;

import com.doctorbookingapp.entity.Hospital;
import com.doctorbookingapp.payload.HospitalDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByName(String name);

    Optional<List<Hospital>> findByLocation(String location);


    //    @Query("SELECT h FROM Hospital h LEFT JOIN FETCH h.departments d LEFT JOIN FETCH h.wards w LEFT JOIN FETCH w.beds WHERE h.name = :name")
//    @Query("SELECT h FROM Hospital h LEFT JOIN FETCH h.departments d LEFT JOIN FETCH h.wards w WHERE h.name = :name")
//    Optional<Hospital> searchHospitalByNameWithDetails(String name);
}
package com.doctorbookingapp.repository;

import com.doctorbookingapp.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepository extends JpaRepository<Bed, Long> {
}
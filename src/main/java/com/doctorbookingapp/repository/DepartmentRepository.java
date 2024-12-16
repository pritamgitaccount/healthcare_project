package com.doctorbookingapp.repository;

import com.doctorbookingapp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByHospitalId(Long id);
}
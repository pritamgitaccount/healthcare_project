package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.Doctor;

import java.util.List;

public interface DoctorService {

    public Doctor addDoctor(Doctor doctor);
    public List<Doctor> searchByNameOrSpecializationOrHospital(String search);

    public Doctor updateDoctorById(Long id ,Doctor doctor);
}

package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.DoctorDto;
import com.doctorbookingapp.repository.DoctorRepository;
import com.doctorbookingapp.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }


    @Override
    public List<Doctor> searchByNameOrSpecializationOrHospital(String search) {
        return doctorRepository.searchByNameOrSpecializationOrHospital(search);
    }

    @Override
    public Doctor updateDoctorById(Long id, Doctor doctor) {
        Doctor existedDoctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor not found with id " + id)
        );
        existedDoctor.setDoctorName(doctor.getDoctorName());
        existedDoctor.setSpecialization(doctor.getSpecialization());
        existedDoctor.setHospital(doctor.getHospital());
        existedDoctor.setDescriptions(doctor.getDescriptions());
        existedDoctor.setQualification(doctor.getQualification());
        existedDoctor.setExperience(doctor.getExperience());

        return doctorRepository.save(existedDoctor);
    }
}

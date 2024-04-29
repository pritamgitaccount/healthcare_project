package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Doctor;
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
}

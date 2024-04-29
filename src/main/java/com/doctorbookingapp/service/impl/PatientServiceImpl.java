package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.PatientDto;
import com.doctorbookingapp.repository.PatientRepository;
import com.doctorbookingapp.service.PatientService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        // Map PatientDto to Patient entity using ModelMapper
        Patient patient = modelMapper.map(patientDto, Patient.class);
        // Save patient
        Patient savedPatient = patientRepository.save(patient);
        // Map saved Patient back to PatientDto
        return modelMapper.map(savedPatient, PatientDto.class);

    }


    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found with Id :" + id));
    }


    @Override
    public void deletePatientById(long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient not found with Id :" + id));
        patientRepository.deleteById(id);
    }

    @Override
    public Patient updatePatient(Long patientId, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with Id: " + patientId));

        // Configure ModelMapper to exclude null properties during mapping
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        // Map all fields except the ID
        modelMapper.map(updatedPatient, existingPatient);

        return patientRepository.save(existingPatient);
    }

    PatientDto mapToDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);

    }

    Patient mapToPatient(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);

    }
}



package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.PatientDto;
import com.doctorbookingapp.payload.UpdateResponse;
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
        Patient patient = mapToPatient(patientDto);
        patientRepository.save(patient);
        return mapToDto(patient);

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
    public UpdateResponse updatePatient(Long patientId, PatientDto patientDto) {
        // Fetch the existing patient by ID
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with Id: " + patientId));

        // Map the DTO to a temporary patient object
        Patient tempPatient = mapToPatient(patientDto);

        // Update the existing patient's fields
        existingPatient.setName(tempPatient.getName());
        existingPatient.setEmail(tempPatient.getEmail());
        existingPatient.setMobile(tempPatient.getMobile());
        existingPatient.setAge(tempPatient.getAge());
        existingPatient.setDisease(tempPatient.getDisease());

        // Save the updated patient back to the repository
        patientRepository.save(existingPatient);

        // Prepare the response with a success message and existingPatient converted to PatientDto
        PatientDto updatedPatientDto = mapToDto(existingPatient);
        String message = "Patient with Id " + patientId + " updated successfully.";
        return new UpdateResponse(updatedPatientDto, message);
    }


    PatientDto mapToDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    Patient mapToPatient(PatientDto patientDto) {
        return modelMapper.map(patientDto, Patient.class);
    }

}



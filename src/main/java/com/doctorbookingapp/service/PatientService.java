package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.payload.PatientDto;
import com.doctorbookingapp.payload.UpdateResponse;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto);

    public Patient getPatientById(Long id);

 

    public void deletePatientById(long id) ;



    UpdateResponse updatePatient(Long patientId, PatientDto patientDto);
}

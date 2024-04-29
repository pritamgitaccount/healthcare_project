package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.payload.PatientDto;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto);

    public Patient getPatientById(Long id);

 

    public void deletePatientById(long id) ;



    Patient updatePatient(Long patientId, Patient updatedPatient);
}

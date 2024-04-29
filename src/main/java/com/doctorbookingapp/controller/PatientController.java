package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.payload.PatientDto;
import com.doctorbookingapp.repository.PatientRepository;
import com.doctorbookingapp.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;


    //http://localhost:8080/api/patients
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        PatientDto savedPatient = patientService.createPatient(patientDto);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/patients/{patientId}
    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable Long patientId) {
        Patient patient = patientService.getPatientById(patientId);

        if (patient == null) {
            return new ResponseEntity<>("Patient not found with Id :" + patientId, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(patient);
        }
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatientById(@PathVariable long patientId) {
        patientService.deletePatientById(patientId);

        return new ResponseEntity<>("Patient has deleted with Id : " + patientId, HttpStatus.OK);
    }


    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(patientId, updatedPatient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
}
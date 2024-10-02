package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.payload.PatientDto;
import com.doctorbookingapp.payload.UpdateResponse;
import com.doctorbookingapp.repository.PatientRepository;
import com.doctorbookingapp.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientRepository patientRepository;

    public PatientController(PatientService patientService, PatientRepository patientRepository) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
    }


    //http://localhost:8080/api/patients/addNewPatient
    @PostMapping("/addNewPatient")
    public ResponseEntity<?> createPatient(@RequestBody PatientDto patientDto) {
        if (patientDto == null) {
            return new ResponseEntity<>("Patient data is required", HttpStatus.BAD_REQUEST);
        }

        // Check if mobile number, email, or username already exists
        if (patientRepository.existsByMobile(patientDto.getMobile())) {
            return new ResponseEntity<>("Mobile number is already in use", HttpStatus.BAD_REQUEST);
        }

        if (patientRepository.existsByEmail(patientDto.getEmail())) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        }

        // If all checks pass, proceed to create the patient
        return new ResponseEntity<>(patientService.createPatient(patientDto), HttpStatus.CREATED);
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

    //http://localhost:8080/api/patients/{patientId}
    @DeleteMapping("/{patientId}")
    public ResponseEntity<String> deletePatientById(@PathVariable long patientId) {
        patientService.deletePatientById(patientId);
        return new ResponseEntity<>("Patient has deleted with Id : " + patientId, HttpStatus.OK);
    }

    // Controller: Handle PATCH request
    //http://localhost:8080/api/patients?patientId=123
    @PatchMapping
    public ResponseEntity<UpdateResponse> updatePatient(@RequestParam Long patientId,
                                                                                                           @RequestBody PatientDto updatedPatient) {
        // Call the service method which returns an UpdateResponse
        UpdateResponse updateResponse = patientService.updatePatient(patientId, updatedPatient);
        // Return the response with a success message
        return new ResponseEntity<>(updateResponse, HttpStatus.OK);
    }
}

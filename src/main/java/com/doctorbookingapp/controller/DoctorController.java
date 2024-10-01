package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.payload.DoctorDto;
import com.doctorbookingapp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    //http://localhost:8080/api/doctors/add/doctor

    @PostMapping("/add/doctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.addDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }


    //http://localhost:8080/api/doctors/search?search=yourSearchTerm
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(@RequestParam String search) {
        List<Doctor> doctors = doctorService.searchByNameOrSpecializationOrHospital(search);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    //http://localhost:8080/api/doctors/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctorById(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        Doctor updatedDoctorDetails = doctorService.updateDoctorById(id, doctorDetails);
        return new ResponseEntity<>(updatedDoctorDetails, HttpStatus.OK);
    }

}
package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.payload.DoctorDto;
import com.doctorbookingapp.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // http://localhost:8080/api/doctors/add/doctor
    @Operation(
            summary = "Add a doctor",
            description = "This API adds a new doctor to the system.",
            tags = {"Doctor Management"},
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add/doctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.addDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/doctors/search?search=yourSearchTerm
    @Operation(
            summary = "Search doctors",
            description = "Search for doctors by name, specialization, or hospital.",
            tags = {"Doctor Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(@RequestParam String search) {
        List<Doctor> doctors = doctorService.searchByNameOrSpecializationOrHospital(search);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    // http://localhost:8080/api/doctors/update/{id}
    @Operation(
            summary = "Update doctor by ID",
            description = "Update the details of an existing doctor using their ID.",
            tags = {"Doctor Management"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "Bearer -token")
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateDoctorById(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        Doctor updatedDoctorDetails = doctorService.updateDoctorById(id, doctorDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Doctor profile updated successfully");
        response.put("updatedDoctor", updatedDoctorDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

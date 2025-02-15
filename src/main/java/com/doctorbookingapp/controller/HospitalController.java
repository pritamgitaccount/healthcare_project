package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Hospital;
import com.doctorbookingapp.payload.HospitalDto;
import com.doctorbookingapp.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    /*
    Method: Add new hospital
     */
    //http://localhost:8080/api/hospitals/add-hospital
    @PostMapping("/add-hospital")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addHospital(@Valid @RequestBody HospitalDto hospitalDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>((Objects.requireNonNull(result.getFieldError())).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            hospitalService.addHospital(hospitalDto);
            return new ResponseEntity<>("Hospital added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add hospital :" + e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    /*
    Method :  Find hospital by name
     */
    // http://localhost:8080/api/hospitals/hospital/search?name={name}
    @GetMapping("/hospital/search")
//    @PreAuthorize("permitAll()")
    public ResponseEntity<?> searchHospitalByName(@RequestParam String name) {
        HospitalDto hospitalDto = hospitalService.searchHospitalByName(name);
        if (hospitalDto != null) {
            return ResponseEntity.ok(hospitalDto);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hospital not found");
        }
    }

    /*
    Method :  Update hospital details
     */
    // http://localhost:8080/api/hospitals/update-hospital?id={id}
    @PatchMapping("/update-hospital")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateHospitalDetails(@RequestParam Long id, @RequestBody HospitalDto hospitalDto) {
        HospitalDto updatedHospitalDto = hospitalService.updateHospital(id, hospitalDto);
        return ResponseEntity.ok(updatedHospitalDto);
    }


    //Delete hospital
    //http://localhost:8080/api/hospitals/delete-hospital/{id}
    @DeleteMapping("delete-hospital/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHospitalById(@PathVariable Long id) {
        hospitalService.deleteHospitalById(id);
        return new ResponseEntity<>("Hospital deleted successfully", HttpStatus.OK);
    }

    // Method for finding all hospitals by location
// http://localhost:8080/api/hospitals/hospital-location?location={location}
    @GetMapping("/hospital-location")
    public ResponseEntity<List<HospitalDto>> getHospitalByLocation(@RequestParam String location) {
        // Call the service method to get all hospitals by location
        List<HospitalDto> hospitalDtos = (List<HospitalDto>) hospitalService.getAllHospitalsByLocation(location);

        if (!hospitalDtos.isEmpty()) {
            return ResponseEntity.ok(hospitalDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

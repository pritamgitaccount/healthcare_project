package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Staff;
import com.doctorbookingapp.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@Slf4j
public class StaffController {

    @Autowired
    private StaffService staffService;

    //http://localhost:8080/api/staff/add-staff
    //Method for : Adding a new staff
    @PostMapping("/add-staff")
    public ResponseEntity<Staff> addStaffs(@RequestBody Staff staff) {
        log.info("Received new staff: {}", staff);
        return ResponseEntity.ok(staffService.addStaff(staff));
    }

    //http://localhost:8080/api/staff/remove-staff/{id}
    //Method for : Removing a staff
    @DeleteMapping("/remove-staff/{id}")
    public ResponseEntity<?> removeStaff(@PathVariable Long id) {
        staffService.deleteStaffById(id);
        log.info("Removed staff with ID: {}", id);
        return new ResponseEntity<>("Successfully removed staff", HttpStatus.OK);
    }

    //http://localhost:8080/api/staff/update-staff/{id}
    //Method for : Updating a staff
    @PutMapping("/update-staff/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
        log.info("Updated staff with ID: {} with new details: {}", id, staffDetails);
        return ResponseEntity.ok(staffService.updateStaff(id, staffDetails));
    }

    //http://localhost:8080/api/staff/get-staff/{id}
    //Method for : Get staff by id
    @GetMapping("/get-staff/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        log.info("Retrieved staff with ID: {}", id);
        return ResponseEntity.ok(staffService.getStaffById(id));
    }
}

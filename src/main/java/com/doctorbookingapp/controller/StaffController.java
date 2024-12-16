package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Staff;
import com.doctorbookingapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;


    @PostMapping("/add-staff")
    public ResponseEntity<Staff>addStaffs(@RequestBody Staff staff) {
        return ResponseEntity.ok(staffService.addStaff(staff));
    }


}

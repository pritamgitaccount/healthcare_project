package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.Staff;

public interface StaffService {
    // Method to add staff
    public Staff addStaff(Staff staff);

    // Method to get staff by id
    public Staff getStaffById(Long id);

    // Method to delete staff by id
    void deleteStaffById(Long id);

    // Method to update staff details
    public Staff updateStaff(Long id, Staff staff);
}


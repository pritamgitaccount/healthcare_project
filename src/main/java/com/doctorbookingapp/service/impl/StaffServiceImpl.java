package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Staff;
import com.doctorbookingapp.repository.StaffRepository;
import com.doctorbookingapp.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired  //Autowired annotation is used to inject the bean into the target class.
    private StaffRepository staffRepository;


    @Override
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff getStaffById(Long id) {
        return staffRepository.getReferenceById(id);
    }

    @Override
    public void deleteStaffById(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        Staff existingStaff = staffRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Could not find staff"));
        Staff staffs = new Staff();
        staffs.setName(existingStaff.getName());
        staffs.setRole(existingStaff.getRole());
        staffs.setShift(existingStaff.getShift());
        return staffs;
    }
}

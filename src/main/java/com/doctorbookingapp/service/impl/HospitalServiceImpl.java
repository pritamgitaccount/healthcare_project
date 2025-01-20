package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Department;
import com.doctorbookingapp.entity.Hospital;
import com.doctorbookingapp.entity.Ward;
import com.doctorbookingapp.exception.HospitalPresentException;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.DepartmentDto;
import com.doctorbookingapp.payload.HospitalDto;
import com.doctorbookingapp.payload.WardDto;
import com.doctorbookingapp.repository.DepartmentRepository;
import com.doctorbookingapp.repository.HospitalRepository;
import com.doctorbookingapp.repository.WardRepository;
import com.doctorbookingapp.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;


    @Override
    public HospitalDto addHospital(HospitalDto hospitalDto) {
        // Check if a hospital with the same name already exists
        if (hospitalRepository.findByName(hospitalDto.getName()).isPresent()) {
            throw new HospitalPresentException("A hospital with the name " + hospitalDto.getName() + " already exists.");
        }

        // Map the DTO to an entity
        Hospital hospital = mapToEntity(hospitalDto);

        // Save the hospital entity to the repository
        hospitalRepository.save(hospital);

        // Map the saved entity back to a DTO and return
        return mapToDto(hospital);
    }

    @Override
    public HospitalDto searchHospitalByName(String name) {
        Optional<Hospital> optionalHospital = hospitalRepository.findByName(name);
        if (optionalHospital.isPresent()) {
            Hospital hospital = optionalHospital.get();

            // Fetch departments and wards associated with the hospital
            List<Department> departments = departmentRepository.findByHospitalId(hospital.getId());
            List<Ward> wards = wardRepository.findByHospitalId(hospital.getId());

            // Map hospital entity to DTO
            HospitalDto hospitalDto = mapToDto(hospital);

            // Map departments and wards to DTOs and set them in HospitalDto
            hospitalDto.setDepartments(mapDepartmentsToDto(departments));
            hospitalDto.setWards(mapWardsToDto(wards));

            return hospitalDto;
        } else {
            throw new ResourceNotFoundException("No hospital found with Name: " + name);
        }
    }

    @Override
    public void deleteHospitalById(Long id) {
        // Implement delete logic if needed
        hospitalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Hospital not found with Id: " + id)
        );
        hospitalRepository.deleteById(id);
        // logger.info("Hospital deleted successfully");
        log.info("Hospital deleted successfully");
    }

    @Override
    public HospitalDto updateHospital(Long hospitalId, HospitalDto hospitalDto) {
        Hospital existingHospital = hospitalRepository.findById(hospitalId).orElseThrow(
                () -> new ResourceNotFoundException("Hospital not found with Id: " + hospitalId)
        );
        if (hospitalDto.getName() != null) {
            existingHospital.setName(hospitalDto.getName());
        }
        if (hospitalDto.getLocation() != null) {
            existingHospital.setLocation(hospitalDto.getLocation());
        }
        if (hospitalDto.getContactInfo() != null) {
            existingHospital.setContactInfo(hospitalDto.getContactInfo());
        }

        hospitalRepository.save(existingHospital);
        log.info("Hospital details updated successfully");
        return mapToDto(existingHospital);
    }

    @Override
    public List<HospitalDto> getAllHospitalsByLocation(String location) {
        List<Hospital> hospitals = hospitalRepository.findByLocation(location)
                .orElseThrow(() -> new ResourceNotFoundException("No hospitals found with Location: " + location));

        // Map each Hospital entity to HospitalDto
        return hospitals.stream().map(this::mapToDto).toList();
    }


    // Mapping methods
    private HospitalDto mapToDto(Hospital hospital) {
        return modelMapper.map(hospital, HospitalDto.class);
    }

    private Hospital mapToEntity(HospitalDto hospitalDto) {
        return modelMapper.map(hospitalDto, Hospital.class);
    }

    private List<DepartmentDto> mapDepartmentsToDto(List<Department> departments) {
        return departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .toList();
    }

    private List<WardDto> mapWardsToDto(List<Ward> wards) {
        return wards.stream()
                .map(ward -> modelMapper.map(ward, WardDto.class))
                .toList();
    }
}

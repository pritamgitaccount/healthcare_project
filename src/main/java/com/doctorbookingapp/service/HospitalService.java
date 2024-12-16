package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.Hospital;
import com.doctorbookingapp.payload.HospitalDto;

public interface HospitalService {

    //Method to add hospital
    public HospitalDto addHospital(HospitalDto hospitalDto);

    //Method to get hospital by name
    public HospitalDto searchHospitalByName(String name);

    //Method to delete hospitals by id
    public void deleteHospitalById(Long id);

    //Method to update hospital details
    public HospitalDto updateHospital(Long hospitalId, HospitalDto hospitalDto);

    //Method to get all hospitals by location
    public Iterable<HospitalDto> getAllHospitalsByLocation(String location);


}

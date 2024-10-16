package com.doctorbookingapp.ServiceTest;

import com.doctorbookingapp.entity.Doctor;
import com.doctorbookingapp.repository.DoctorRepository;
import com.doctorbookingapp.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    // Add Mockito mock objects for DoctorService
    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorServiceImpl;

    //Test cases for adding doctor
    @Test
    public void addDoctorTest() {
        //Arrange
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr. Smith");
        doctor.setSpecialization("Cardiologist");
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        //Act
        Doctor savedDoctor = doctorServiceImpl.addDoctor(doctor);

        // Assert
        assertNotNull(savedDoctor);  // Ensure doctor object is not null
        assertEquals("Dr. Smith", savedDoctor.getDoctorName()); // Check doctor name
        assertEquals("Cardiologist", savedDoctor.getSpecialization()); // Check specialization
        verify(doctorRepository, times(1)).save(doctor);  // Ensure save is called once
    }

    @Test
    public void testSearchByNameOrSpecializationOrHospital() {
        // Arrange
        String searchQuery = "Cardiology";
        List<Doctor> doctors = List.of(new Doctor("Dr. Smith", "Cardiology", "Apollo Hospital"));
        when(doctorRepository.searchByNameOrSpecializationOrHospital(searchQuery)).thenReturn(doctors);

        // Act
        List<Doctor> result = doctorServiceImpl.searchByNameOrSpecializationOrHospital(searchQuery);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dr. Smith", result.get(0).getDoctorName());
        verify(doctorRepository, times(1)).searchByNameOrSpecializationOrHospital(searchQuery);
    }

    @Test
    public void testUpdateDoctorById_Success() {
        // Arrange
        Long doctorId = 1L;
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(doctorId);
        existingDoctor.setDoctorName("Dr. Jane Smith");

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setDoctorName("Dr. Jane Smith");
        updatedDoctor.setSpecialization("Cardiology");
        updatedDoctor.setHospital("City Hospital");
        updatedDoctor.setDescriptions("Experienced Cardiologist");
        updatedDoctor.setQualification("MBBS, MD");
        updatedDoctor.setExperience(15);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(existingDoctor)).thenReturn(existingDoctor);

        // Act
        Doctor result = doctorServiceImpl.updateDoctorById(doctorId, updatedDoctor);

        // Assert
        assertNotNull(result);
        assertEquals("Dr. Jane Smith", result.getDoctorName());
        assertEquals("Cardiology", result.getSpecialization());
        assertEquals("City Hospital", result.getHospital());
        assertEquals("Experienced Cardiologist", result.getDescriptions());
        assertEquals("MBBS, MD", result.getQualification());
        assertEquals(15, result.getExperience());

        verify(doctorRepository, times(1)).findById(doctorId);
        verify(doctorRepository, times(1)).save(existingDoctor);
    }


}

package com.doctorbookingapp.ServiceTest;

import com.doctorbookingapp.entity.Hospital;
import com.doctorbookingapp.payload.HospitalDto;
import com.doctorbookingapp.repository.HospitalRepository;
import com.doctorbookingapp.service.impl.HospitalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This is a test class for the HospitalServiceImpl class, which contains a test case
 * for the addHospital method to check its behavior under normal conditions.
 */
@ExtendWith(MockitoExtension.class) // Enables Mockito extension for handling mock annotations in JUnit 5
public class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepository; // Mocked HospitalRepository to simulate database interactions

    @Mock
    private ModelMapper modelMapper;  // Mocked ModelMapper to simulate object mapping between DTOs and entities

    @InjectMocks
    private HospitalServiceImpl hospitalServiceImpl; // Injects the mocks into HospitalServiceImpl to test its logic

    @Test
    public void addHospital_Success() {
        // Arrange section: Sets up the data and mock behavior for the test

        // Creating a HospitalDto object with test data for adding a new hospital
        HospitalDto dto = new HospitalDto();
        dto.setName("Maa Hospital");

        // Creating a Hospital entity object with similar data for simulating persistence behavior
        Hospital hospital = new Hospital();
        hospital.setName("Maa Hospital");

        // Mocking repository behavior: When findByName is called, return Optional.empty(), meaning no hospital exists with this name
        when(hospitalRepository.findByName(dto.getName())).thenReturn(Optional.empty());

        // Mocking repository behavior: When save is called on any Hospital object, return the created hospital object
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(hospital);

        // Mocking ModelMapper behavior for converting DTO to entity: When ModelMapper maps dto to Hospital.class, return the hospital entity
        when(modelMapper.map(dto, Hospital.class)).thenReturn(hospital);

        // Mocking ModelMapper behavior for converting entity back to DTO: When ModelMapper maps hospital to HospitalDto.class, return dto
        when(modelMapper.map(hospital, HospitalDto.class)).thenReturn(dto);

        // Act section: Executes the method being tested
        HospitalDto result = hospitalServiceImpl.addHospital(dto);

        // Assert section: Verifies the expected results
        // Check that the name of the resulting HospitalDto matches the name of the input data
        assertEquals("Maa Hospital", result.getName());

        // Verify that the save method was called on hospitalRepository with any Hospital object, confirming save behavior
        verify(hospitalRepository).save(any(Hospital.class));
    }
}

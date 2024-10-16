package com.doctorbookingapp.ServiceTest;

import com.doctorbookingapp.entity.Patient;
import com.doctorbookingapp.payload.PatientDto;
import com.doctorbookingapp.repository.PatientRepository;
import com.doctorbookingapp.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    public void testCreatePatient() {
        // TODO: Write test case for createPatient method
        //Arrange
        PatientDto dto = new PatientDto();
        dto.setPatientId(1L);
        dto.setName("Mike");
        dto.setMobile("9874456123");
        dto.setEmail("mike@example.com");
        dto.setAge(34);
        dto.setDisease("Cold and cough");

        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setName("Mike");
        patient.setMobile("9874456123");
        patient.setEmail("mike@example.com");
        patient.setAge(34);
        patient.setDisease("Cold and cough");

        when(modelMapper.map(dto, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(dto);


        //Act
        PatientDto result = patientService.createPatient(dto);
        //Assert
        assertNotNull(result);
        assertEquals(1L, result.getPatientId());
        assertEquals("Mike", result.getName());
        assertEquals("9874456123", result.getMobile());
        assertEquals("mike@example.com", result.getEmail());
        assertEquals(34, result.getAge());
        assertEquals("Cold and cough", result.getDisease());

        verify(modelMapper,times(1)).map(dto, Patient.class);
        verify(patientRepository,times(1)).save(patient);
        verify(modelMapper, times(1)).map(patient, PatientDto.class);




    }



}

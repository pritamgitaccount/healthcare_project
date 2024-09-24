package com.doctorbookingapp.ServiceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.doctorbookingapp.entity.Role;
import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.repository.RoleRepository;
import com.doctorbookingapp.repository.UserRepository;
import com.doctorbookingapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class) // Use this annotation to enable Mockito in JUnit 5
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService; // Assuming the service class is UserServiceImpl

    private SignUpDto signUpDto;

    @BeforeEach
    public void setUp() {
        // Create a mock SignUpDto
        signUpDto = new SignUpDto();
        signUpDto.setFirstName("John");
        signUpDto.setLastName("Doe");
        signUpDto.setUsername("johndoe");
        signUpDto.setEmail("john@example.com");
        signUpDto.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        signUpDto.setRoles(roles);
    }

    @Test
    public void testAddUser_Success() {
        // Mock behavior for userRepository and bCryptPasswordEncoder
        when(userRepository.findByUsername(signUpDto.getUsername())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(signUpDto.getPassword())).thenReturn("encodedPassword");

        // Mock roleRepository behavior
        Role mockRole = new Role();
        mockRole.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(mockRole);

        // Create User mock for saved user
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName(signUpDto.getFirstName());
        savedUser.setLastName(signUpDto.getLastName());
        savedUser.setUsername(signUpDto.getUsername());
        savedUser.setEmail(signUpDto.getEmail());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Call the method to test
        SignUpDto result = userService.addUser(signUpDto);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(signUpDto.getFirstName(), result.getFirstName());
        assertEquals(signUpDto.getLastName(), result.getLastName());
        assertEquals(signUpDto.getUsername(), result.getUsername());
        assertEquals(signUpDto.getEmail(), result.getEmail());
        assertEquals(signUpDto.getRoles(), result.getRoles());

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(signUpDto.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
        verify(bCryptPasswordEncoder, times(1)).encode(signUpDto.getPassword());
        verify(roleRepository, times(1)).findByName("ROLE_USER");
    }

    @Test
    public void testAddUser_UserAlreadyExists() {
        // Mock userRepository to return a user indicating the user already exists
        when(userRepository.findByUsername(signUpDto.getUsername())).thenReturn(Optional.of(new User()));

        // Test the exception
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.addUser(signUpDto);
        });

        // Verify interactions
        verify(userRepository, times(1)).findByUsername(signUpDto.getUsername());
        verify(userRepository, never()).save(any(User.class));
    }
}

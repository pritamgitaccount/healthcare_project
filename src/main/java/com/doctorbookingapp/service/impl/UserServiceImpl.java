package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Role;
import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.entity.UserRole;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.repository.RoleRepository;
import com.doctorbookingapp.repository.UserRepository;
import com.doctorbookingapp.jwt.JwtService;
import com.doctorbookingapp.service.CustomUserDetailsService;
import com.doctorbookingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  //  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // Use BCryptPasswordEncoder
    private final CustomUserDetailsService userDetailsService;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SignUpDto addUser(SignUpDto signUpDto) {
        if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            throw new ResourceNotFoundException("User already exists");
        }

        User userSignUp = new User();
        userSignUp.setFirstName(signUpDto.getFirstName());
        userSignUp.setLastName(signUpDto.getLastName());
        userSignUp.setUsername(signUpDto.getUsername());
        userSignUp.setEmail(signUpDto.getEmail());
        userSignUp.setPassword(bCryptPasswordEncoder.encode(signUpDto.getPassword())); // Use BCryptPasswordEncoder

        // Handle roles dynamically
        Set<UserRole> userRoles = new HashSet<>();
        for (String roleName : signUpDto.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }

            UserRole userRole = new UserRole();
            userRole.setUser(userSignUp);
            userRole.setRole(role);

            userRoles.add(userRole);
        }
        userSignUp.setUserRoles(userRoles);

        User savedUser = userRepository.save(userSignUp);

        // Map User to SignUpDto
        SignUpDto dto = new SignUpDto();
        dto.setId(savedUser.getId());
        dto.setFirstName(savedUser.getFirstName());
        dto.setLastName(savedUser.getLastName());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        dto.setRoles(signUpDto.getRoles()); // Pass the roles back if needed

        return dto;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with Id :" + id)
        );
        userRepository.deleteById(id);
    }

//    @Override
//    public JwtResponse loginUser(SignInDto signInDto) {
//        if (!signInDto.getUsername().isEmpty() && !signInDto.getPassword().isEmpty()) {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword())
//            );
//            UserDetails userDetails = userDetailsService.loadUserByUsername(signInDto.getUsername());
//            String token = jwtService.generateToken(userDetails);
//            return new JwtResponse(token);
//        } else {
//            throw new ResourceNotFoundException("Invalid username or password");
//        }
//    }
}

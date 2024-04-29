package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Role;
import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.repository.RoleRepository;
import com.doctorbookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?>createUser(@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return ResponseEntity.badRequest().body("User already exist");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        // Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        Role roles = roleRepository.findByName("ROLE_USER").get();
        Set<Role> role = new HashSet<>();
        role.add(roles);
        user.setRoles(role);
        // user.setRoles(Collections.singleton(roles));
        User savedUser = userRepository.save(user);
        //Here if we return savedUser the password also will be return, but it is a security thread
        //So, we will copy the data back to SignUpDto
        SignUpDto dto = new SignUpDto();
        dto.setName(savedUser.getName());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody SignInDto signInDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.getUsernameOrEmail(), signInDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }


}

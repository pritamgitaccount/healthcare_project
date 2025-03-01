package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.payload.JwtResponse;
import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;

public interface UserService {
    public SignUpDto addUser(SignUpDto signUpDto);

    void deleteUserById(Long id);

    User getUserById(Long userId);

//    public JwtResponse loginUser(SignInDto signInDto);
}

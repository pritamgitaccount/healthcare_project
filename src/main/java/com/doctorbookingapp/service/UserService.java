package com.doctorbookingapp.service;

import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;

public interface UserService {
    public SignUpDto addUser(SignUpDto signUpDto);

    public String verifyLogin(SignInDto signInDto);
}

package com.doctorbookingapp.payload;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
public class SignUpDto {

    private Long id;

    @NotEmpty(message = "Name cannot be blank")
    private String firstName;


    private String lastName;

    @NotEmpty(message = "Username cannot be blank")
    private String username;

    @NotEmpty(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "User role cannot be blank")
    private String userRole;
}
